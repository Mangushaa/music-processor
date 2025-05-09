package org.example.intergration.client.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.configuration.properties.AwsProperties;
import org.example.intergration.client.ResourceClient;
import org.example.intergration.client.dto.ResourceContentResponse;
import org.example.intergration.client.dto.ResourceUploadResponse;
import org.example.intergration.client.exception.ResourceNotFoundInS3Exception;
import org.example.model.Resource;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class S3ResourceClient implements ResourceClient {

    private final S3Client s3Client;

    private final AwsProperties awsProperties;

    @Override
    public ResourceUploadResponse uploadResource(byte[] resource) {
        PutObjectRequest putObjectRequest = createPutObjectRequest();
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(resource));
        return new ResourceUploadResponse(createResourceLocation(putObjectRequest));
    }

    @Override
    public ResourceContentResponse getResourceContent(Resource resource) {
        ResponseInputStream<GetObjectResponse> object = s3Client.getObject(createGetObjectRequest(resource));
        ResourceContentResponse resourceContentResponse = new ResourceContentResponse();
        try {
            resourceContentResponse.setContent(object.readAllBytes());
            return resourceContentResponse;
        } catch (IOException e) {
            log.warn("Error reading resource from S3");
            throw new ResourceNotFoundInS3Exception();
        }
    }

    private String createResourceLocation(PutObjectRequest putObjectRequest) {
        return s3Client.utilities().getUrl(b -> b.bucket(putObjectRequest.bucket()).key(putObjectRequest.key())).toString();
    }

    private PutObjectRequest createPutObjectRequest() {
        return PutObjectRequest.builder()
                .bucket(awsProperties.getS3().getResourceBucket())
                .key(UUID.randomUUID().toString())
                .contentType("audio/mpeg")
                .build();
    }

    private GetObjectRequest createGetObjectRequest(Resource resource) {
        String[] resourceLocationComponents = resource.getResourceLocation().split("/");
        int resourceLocationComponentsLength = resourceLocationComponents.length;
        return GetObjectRequest.builder().responseContentType("audio/mpeg")
                .bucket(resourceLocationComponents[resourceLocationComponentsLength - 2])
                .key(resourceLocationComponents[resourceLocationComponentsLength - 1])
                .build();
    }
}
