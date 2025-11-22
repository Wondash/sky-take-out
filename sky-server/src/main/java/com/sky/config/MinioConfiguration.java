package com.sky.config;

import com.sky.properties.MinioProperties;
import com.sky.utils.MinioUtil;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Slf4j
@Component
public class MinioConfiguration {

    @Value("${sky.minio.endpoint}")
    private String endpoint;
    @Value("${sky.minio.access-key-id}")
    private String accessKeyId;
    @Value("${sky.minio.access-key-secret}")
    private String accessKeySecret;
    @Value("${sky.minio.bucket-name}")
    private String bucketName;

    /**
     * 创建 MinioClient Bean，使其可以被 Spring 容器管理和注入。
     * @return MinioClient 实例
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKeyId, accessKeySecret)
                .build();
    }

    @Autowired
    private MinioClient minioClient;

    public void setBucketPolicy(String bucketName) {
        try {
            // 设置桶为公共读策略
            String policy = """
                    {
                        "Version": "2012-10-17",
                        "Statement": [
                            {
                                "Effect": "Allow",
                                "Principal": {"AWS": ["*"]},
                                "Action": ["s3:GetObject"],
                                "Resource": ["arn:aws:s3:::%s/*"]
                            }
                        ]
                    }
                    """.formatted(bucketName);

            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(bucketName)
                            .config(policy)
                            .build()
            );
            System.out.println("桶 " + bucketName + " 已设置为公共读权限");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("设置桶权限失败: " + e.getMessage());
        }
    }
}
