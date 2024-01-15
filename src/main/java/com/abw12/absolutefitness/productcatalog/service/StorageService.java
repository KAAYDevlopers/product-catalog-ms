package com.abw12.absolutefitness.productcatalog.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.time.Instant;
import java.util.Date;

@Service
public class StorageService {

    private final AmazonS3 s3Client;

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
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName,objectKey)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
        return s3Client.generatePresignedUrl(request);
    }
}
