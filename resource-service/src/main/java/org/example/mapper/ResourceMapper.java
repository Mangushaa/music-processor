package org.example.mapper;

import org.example.dto.GetResourceResponse;
import org.example.dto.UploadResourceResponse;
import org.example.intergration.client.dto.ResourceContentResponse;
import org.example.intergration.client.dto.ResourceUploadResponse;
import org.example.intergration.producer.dto.ResourceCreatedEvent;
import org.example.model.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Mapper(componentModel = "spring")
public interface ResourceMapper {
    Resource resourceUploadResponseDtoToResource(ResourceUploadResponse resourceUploadResponseDto);

    UploadResourceResponse resourceModelToUploadResourceResponse(Resource resourceModel);

    GetResourceResponse resourceModelToGetResourceResponse(Resource resourceModel);

    ResourceUploadResponse putObjectResponseToResourceUploadResponseDto(PutObjectResponse putObjectResponse);
    GetResourceResponse resourceContentResponseToGetResourceResponse (ResourceContentResponse response);

    @Mapping(source = "id", target = "resourceId")
    ResourceCreatedEvent resourceToResourceCreatedEvent(Resource resource);
}
