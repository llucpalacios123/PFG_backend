package com.lluc.backend.shopapp.shopapp.services.Implementations;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageUploadService {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    private S3Client s3Client;

    public String uploadImage(MultipartFile file) throws IOException {
        String key = "products/" + file.getOriginalFilename();

        // Subir a S3
        s3Client.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build(),
            RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        return key;
    }
    public void deleteImage(String key) {
        s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key));
    }
}