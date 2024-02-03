package com.abw12.absolutefitness.productcatalog.controller;

import com.abw12.absolutefitness.productcatalog.dto.ImageMetaDataDTO;
import com.abw12.absolutefitness.productcatalog.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/catalog/storage")
public class StorageController {

    @Autowired
    private StorageService storageService;

    private static final Logger logger = LoggerFactory.getLogger(StorageController.class);


    @GetMapping("/generate-presigned-url")
    public ResponseEntity<?> generatePresignedUrl(@RequestParam String bucketName, String objectKey){
        logger.info("Inside generatePresignedUrl Rest API");
        try{
            return new ResponseEntity<>(storageService.generatePresignedUrl(bucketName,objectKey,3600), HttpStatus.OK);
        }catch (Exception e){
            logger.error("Error while creating a pre-signed URL :: {}",e.getMessage());
            throw e;
        }
    }

//    @PostMapping(path = "/{productId}/variants/{variantId}/images" ,  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Flux<ResponseEntity<String>> uploadImageToSpaces(@PathVariable String productId,
//                                                            @PathVariable String variantId,
//                                                            @RequestPart("images") Flux<FilePart> images) {
//
//        return images
////                .filter(StorageService::isValidFormat) //validation check for filetype
////                .filter(StorageService::isValidSize) //validation check for file size
//                .flatMap(image ->
//                        storageService.uploadImage( productId,variantId,image)
//                                .map(url -> ResponseEntity.ok().body("Uploaded: " + url))
//                                .doOnError(error -> logger.error("Error while uploading a file with productId={} and variantId={} :: ERROR => {}",productId,variantId,error.getMessage()))
//                                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage())))
//                )
//                .switchIfEmpty(Mono.just(ResponseEntity.badRequest().body("Invalid file format or size")));
//    }

//    public ResponseEntity<?> uploadImages(@PathVariable String productId, @PathVariable String variantId, @RequestPart MultipartFile[] images){
//
//        for(MultipartFile image : images){
//            if(!StorageService.isValidSize(image) && !StorageService.isValidFormat(image)){
//
//                 ResponseEntity.badRequest().body("Image size exceeds limit");
//            }else{
//                storageService.uploadFileAsync(productId,variantId,image).thenAccept(url ->{
//
//                });
//            }
//        }
//    }

    @PostMapping(path = "/product/{productId}/variants/{variantId}/images" ,  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(@PathVariable String productId,
                                          @PathVariable String variantId,
                                          @RequestParam("images") MultipartFile[] images) {

        for (MultipartFile image : images) {
            CompletableFuture<ImageMetaDataDTO> uploadFuture = storageService.uploadFileAsync(productId,variantId, image);
            uploadFuture.thenAccept(imageMetaData -> {
                // Process the URL, e.g., save to DB (this executed synchronously )
                storageService.saveImageMetaDataInDB(productId,variantId,imageMetaData);
            }).exceptionally(e -> {
                // Log and handle the exception
                logger.error("Error while uploading a file with productId={} and variantId={} :: ERROR => {}",productId,variantId,e.getMessage());
                return null;
            });
        }
        return ResponseEntity.accepted().body("Upload in progress...");
    }
}
