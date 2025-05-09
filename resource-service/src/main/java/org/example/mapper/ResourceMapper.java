package org.example.mapper;

import org.example.dto.GetResourceResponse;
import org.example.dto.UploadResourceResponse;
import org.example.intergration.client.dto.ResourceContentResponse;
import org.example.intergration.client.dto.ResourceUploadResponse;
import org.example.intergration.producer.dto.ResourceCreatedEvent;
import org.example.model.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResourceMapper {
    Resource resourceUploadResponseDtoToResource(ResourceUploadResponse resourceUploadResponse);

    UploadResourceResponse resourceModelToUploadResourceResponse(Resource resourceModel);

    GetResourceResponse resourceModelToGetResourceResponse(Resource resourceModel);

    GetResourceResponse resourceContentResponseToGetResourceResponse (ResourceContentResponse response);

    @Mapping(source = "id", target = "resourceId")
    ResourceCreatedEvent resourceToResourceCreatedEvent(Resource resource);
}
