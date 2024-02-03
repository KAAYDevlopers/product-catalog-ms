package com.abw12.absolutefitness.productcatalog.service;

import com.abw12.absolutefitness.productcatalog.dto.ImageMetaDataDTO;
import com.abw12.absolutefitness.productcatalog.entity.VariantImagesDAO;
import com.abw12.absolutefitness.productcatalog.repository.ImageTableRepository;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class StorageService {

    @Value("${spaces.bucketName}")
    private String bucketName;

    private final AmazonS3 s3Client;

    @Autowired
    private ImageTableRepository imageTableRepository;

    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);

    private final ExecutorService fileUploadExecutor = Executors.newFixedThreadPool(5); // Customize the thread pool size

    private static final long MAX_SIZE = 5 * 1024 * 1024; // 5 MB in bytes
    private static final List<String> ALLOWED_FORMATS = Arrays.asList("image/png", "image/jpeg", "image/jpg");

    public static boolean isValidFormat(MultipartFile filePart) {
        return ALLOWED_FORMATS.contains(Objects.requireNonNull(filePart.getContentType()));
    }

    public static boolean isValidSize(MultipartFile filePart) {
        return filePart.getSize() <= MAX_SIZE;
    }
    public String getObjectKey(String productId, String variantId){
        return "products/" + productId + "/variants/" + variantId;
    }

    //When the application is about to shut down, Spring calls the onDestroy() method.
    //The method attempts to shut down the ExecutorService gracefully, first allowing existing tasks to complete and then forcibly shutting down
    // if tasks do not complete within the specified timeout.
    @PreDestroy
    public void onDestroy() {
        fileUploadExecutor.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!fileUploadExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                fileUploadExecutor.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!fileUploadExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("FileUploadExecutor did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            fileUploadExecutor.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }


    public StorageService(@Value("${spaces.access_key_id}") String accessKey,
                          @Value("${spaces.secret_key}") String secretKey,
                          @Value("${spaces.region}") String spacesRegion,
                          @Value("${spaces.baseUrl}") String spacesBaseUrl){
        BasicAWSCredentials creds = new BasicAWSCredentials(accessKey,secretKey);

        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(creds))
                .withEndpointConfiguration(new AmazonS3ClientBuilder.EndpointConfiguration(spacesBaseUrl, spacesRegion))
                .build();
    }

    public URL generatePresignedUrl(String bucketName, String objectKey,int expirationInSeconds){
        Date expiration = new Date(Instant.now().toEpochMilli() + 1000L * expirationInSeconds);
        S3Object object = s3Client.getObject(bucketName, objectKey);
        System.out.println(object.toString());
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName,objectKey)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
        return s3Client.generatePresignedUrl(request);
    }

    public void saveImageMetaDataInDB(String productId,String variantId,ImageMetaDataDTO imageMetaData){
        VariantImagesDAO variantImagesDAO = new VariantImagesDAO();
        variantImagesDAO.setProductId(productId);
        variantImagesDAO.setVariantId(variantId);
        variantImagesDAO.setImageUrl(imageMetaData.getImageUrl());
        variantImagesDAO.setFileName(imageMetaData.getFileName());
        variantImagesDAO.setFileSize(imageMetaData.getFileSize());
        variantImagesDAO.setContentType(imageMetaData.getContentType());
        VariantImagesDAO storedImageData = imageTableRepository.save(variantImagesDAO);
        logger.info("Image stored with variantId={} => {}",variantId,storedImageData.getFileName());
    }

//    public Mono<String> uploadImage(String productId, String variantId, FilePart filePart) {
//        //'DataBufferUtils.join' it aggregates the incoming data buffers (chunks of the file being uploaded) into a single DataBuffer.
//        return DataBufferUtils.join(filePart.content())
//                .flatMap(dataBuffer -> {
//                    String fileName = getFilePath(productId,variantId) + "/" + filePart.filename();
//                    ObjectMetadata metadata = new ObjectMetadata();
//                    metadata.setContentLength(dataBuffer.readableByteCount()); // readableByteCount method then gives you the size of this aggregated data buffer, which is essentially the size of the file being uploaded.
//                    // Set content type in metadata
//                    if (filePart.headers().getContentType() != null) {
//                        metadata.setContentType(Objects.requireNonNull(filePart.headers().getContentType()).toString());
//                    }
//
//                    InputStream inputStream = dataBuffer.asInputStream();
//                    return Mono.fromCallable(() -> {
//                        try {
//                            s3Client.putObject(baseBucketName, fileName, inputStream, metadata);
//                            return s3Client.getUrl(baseBucketName, fileName).toString();
//                        } finally {
//                            inputStream.close(); // Ensure the stream is closed
//                            DataBufferUtils.release(dataBuffer); // Release the data buffer,After uploading the file, it's important to release the DataBuffer using DataBufferUtils.release(dataBuffer) to avoid memory leaks.
//                        }
//                    }).subscribeOn(Schedulers.boundedElastic());
//                })
//                .onErrorResume(e -> {
//                    // Handle or log the exception
//                    logger.error("Error while uploading the image for productId={} , variantId={} => ERROR :: {}",productId,variantId,e.getStackTrace());
//                    return Mono.just("Error occurred while uploading image: " + e.getMessage());
//                });
//    }

    public CompletableFuture<ImageMetaDataDTO> uploadFileAsync(String productId, String variantId, MultipartFile file) {
        List<Bucket> buckets = s3Client.listBuckets();
        System.out.println(buckets);
//        s3Client.createBucket(bucketName);
        if(!s3Client.doesBucketExistV2(bucketName)){
            logger.error("Bucket does not exist with name={}",bucketName);
            throw new RuntimeException("bucket does not exist");
        }
        return CompletableFuture.supplyAsync(() -> {
            try {
                String objectKey = getObjectKey(productId,variantId) + "/" + file.getOriginalFilename();
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(file.getSize());
                metadata.setContentType(file.getContentType());
                PutObjectRequest uploadImgReq = new PutObjectRequest(bucketName, objectKey, file.getInputStream(), metadata);
                uploadImgReq.withCannedAcl(CannedAccessControlList.PublicRead); //making the uploaded object public
                s3Client.putObject(uploadImgReq);
                String imageUrl = s3Client.getUrl(bucketName, objectKey).toString();
                return new ImageMetaDataDTO(imageUrl,file.getOriginalFilename(),String.valueOf(file.getSize()),file.getContentType());
            } catch (IOException e) {
                logger.error("Error while uploading the image for productId={} , variantId={} => ERROR :: {}",productId,variantId,e.getStackTrace());
                throw new RuntimeException("Failed to upload file" + e.getMessage(), e);
            }
        },fileUploadExecutor);
    }
}
