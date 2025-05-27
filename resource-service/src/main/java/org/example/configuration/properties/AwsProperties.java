package org.example.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("aws")
public class AwsProperties {
    private String region;
    private String url;
    private String accessKey;
    private String secretKey;
}
