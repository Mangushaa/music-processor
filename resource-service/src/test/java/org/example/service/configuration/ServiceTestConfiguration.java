package org.example.service.configuration;

import org.example.intergration.client.impl.S3ResourceClient;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServiceTestConfiguration {

    @Bean
    public S3ResourceClient s3ResourceClient() {
        return Mockito.mock(S3ResourceClient.class);
    }
}
