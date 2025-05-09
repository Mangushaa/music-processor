package org.example.mapper;

import org.example.dto.GetResourceResponse;
import org.example.dto.UploadResourceResponse;
import org.example.intergration.client.dto.ResourceUploadResponseDto;
import org.example.model.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Mapper(componentModel = "spring")
public interface ResourceMapper {
    Resource resourceUploadResponseDtoToResource(ResourceUploadResponseDto resourceUploadResponseDto);

    UploadResourceResponse resoucreModelToUploadResourceResponse(Resource resourceModel);

    GetResourceResponse resourceModelToGetResourceResponse(Resource resourceModel);

    ResourceUploadResponseDto putObjectResponseToResourceUploadResponseDto(PutObjectResponse putObjectResponse);
}
