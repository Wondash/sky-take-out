package com.sky.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import io.minio.*;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.xml.core.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Data
@Component
@AllArgsConstructor
@Slf4j
public class MinioUtil {
    private final MinioClient minioClient;
    @Value("${sky.minio.endpoint}")
    private String endpoint;

    @Value("${sky.minio.bucket-name}")
    private String bucketName;

    // 构造函数注入 MinioClient Bean
    @Autowired
    public MinioUtil(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    public String upload(MultipartFile file, String objectName) {
        try {
            // 检查存储桶是否存在，不存在则创建
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("Bucket '{}' created successfully.", bucketName);
            }

            // 上传文件
            try (InputStream inputStream = file.getInputStream()) {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(bucketName) // 使用配置的 bucketName
                                .object(objectName)  // 使用传入的 objectName 作为文件名
                                .stream(inputStream, file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );
            }

            // 构建并返回文件的访问 URL
            String fileUrl = endpoint + "/" + bucketName + "/" + objectName;
            log.info("文件上传成功，URL: {}", fileUrl);
            return fileUrl;

        } catch (Exception e) {
            log.error("Failed to upload file to MinIO", e);
            throw new RuntimeException("文件上传失败", e);
        }
    }
}
