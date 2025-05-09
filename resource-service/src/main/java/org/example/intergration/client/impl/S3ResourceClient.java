package org.example.intergration.client.impl;

import lombok.RequiredArgsConstructor;
import org.example.configuration.S3ConfigurationProperties;
import org.example.intergration.client.ResourceClient;
import org.example.intergration.client.dto.ResourceUploadResponseDto;
import org.example.mapper.ResourceMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ResourceClient implements ResourceClient {

    private final S3Client s3Client;

    private final S3ConfigurationProperties s3ConfigurationProperties;

    private final ResourceMapper resourceMapper;

    @Override
    public ResourceUploadResponseDto uploadResource(byte[] resource) {
        PutObjectRequest putObjectRequest = createPutObjectRequest();
        return new ResourceUploadResponseDto(createResourceLocation(putObjectRequest));
    }

    private String createResourceLocation(PutObjectRequest putObjectRequest) {
        return s3Client.utilities().getUrl(b -> b.bucket(putObjectRequest.bucket()).key(putObjectRequest.key())).toString();
    }

    private PutObjectRequest createPutObjectRequest() {
         return PutObjectRequest.builder()
                .bucket(s3ConfigurationProperties.getResourceBucket())
                .key(UUID.randomUUID().toString())
                .contentType("audio/mpeg")
                .build();
    }
}
