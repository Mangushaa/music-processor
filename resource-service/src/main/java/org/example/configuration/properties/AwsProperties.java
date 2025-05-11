package org.example.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    private final S3 s3 = new S3();

    private String region;
    private String host;
    private String accessKey;
    private String secretKey;

    @Data
    public static class S3 {
        private String resourceBucket;
    }
}
