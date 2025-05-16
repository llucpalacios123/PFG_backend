package com.lluc.backend.shopapp.shopapp.services.Implementations;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageUploadService {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Autowired
    private S3Client s3Client;

    public String uploadImage(MultipartFile file,String folder) throws IOException {
        // Generar un UUID único para el archivo
        String uniqueId = UUID.randomUUID().toString();
        // Obtener la extensión del archivo original
        String extension = getFileExtension(file.getOriginalFilename());
        // Crear la clave única
        String key = uniqueId + (extension.isEmpty() ? "" : "." + extension);

        // Subir a S3
        s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(folder+ "/"+key)
                    .build(),
                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
        );

        return key;
    }

    // Método auxiliar para obtener la extensión del archivo
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
    public void deleteImage(String key) {
        s3Client.deleteObject(builder -> builder.bucket(bucketName).key(key));
    }
}