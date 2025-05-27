package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class ResourceProcessor
{
    public static void main( String[] args )
    {
        SpringApplication.run(ResourceProcessor.class);
    }
}
