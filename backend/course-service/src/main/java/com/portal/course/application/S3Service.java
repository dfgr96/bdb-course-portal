package com.portal.course.application;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;

@Service
public class S3Service implements IS3Service{
    private final S3Client s3Client;
    private final String bucketName = "portal-courses";

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public List<String> uploadFiles(List<MultipartFile> files, String folder) {
        return files.stream()
                .map(file -> {
                    try {
                        String key = folder + "/" + file.getOriginalFilename();

                        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                                .bucket(bucketName)
                                .key(key)
                                .contentType(file.getContentType())
                                .build();

                        s3Client.putObject(
                                putObjectRequest,
                                RequestBody.fromInputStream(file.getInputStream(), file.getSize())
                        );

                        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
                    } catch (IOException e) {
                        throw new RuntimeException("Error al subir archivo a S3: " + file.getOriginalFilename(), e);
                    }
                }).toList();
    }
}
