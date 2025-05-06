package org.example.mapper;

import org.example.dto.GetResourceResponse;
import org.example.dto.UploadResourceResponse;
import org.example.model.Resource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResourceMapper {
    Resource contentToResourceModel(byte[] content);

    UploadResourceResponse resoucreModelToUploadResourceResponse(Resource resourceModel);

    @Mapping(source = "content", target = "content")
    GetResourceResponse resourceModelToGetResourceResponse(Resource resourceModel);

}
