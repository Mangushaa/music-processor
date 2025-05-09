package org.example.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.dto.DeleteResourceResponse;
import org.example.dto.GetResourceResponse;
import org.example.dto.UploadResourceResponse;
import org.example.intergration.client.ResourceClient;
import org.example.intergration.client.dto.ResourceUploadResponse;
import org.example.intergration.producer.ResourceProducer;
import org.example.intergration.producer.dto.ResourceCreatedEvent;
import org.example.intergration.producer.dto.ResourceDeletedEvent;
import org.example.mapper.ResourceMapper;
import org.example.model.Resource;
import org.example.repository.ResourceRepository;
import org.example.service.ResourceService;
import org.example.service.exception.ResourceNotFoundException;
import org.example.validation.annotation.GivenMimeType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    private final ResourceMapper resourceMapper;

    private final ResourceClient resourceClient;

    private final ResourceProducer resourceProducer;
    
    @Transactional(rollbackFor = Exception.class)
    public UploadResourceResponse uploadResource(
            @Valid @GivenMimeType(mimeType = "audio/mpeg") byte[] resource
    ) {
        ResourceUploadResponse resourceUploadResponse = resourceClient.uploadResource(resource);
        Resource newResource = resourceMapper.resourceUploadResponseDtoToResource(resourceUploadResponse);
        Resource savedResource = resourceRepository.save(newResource);
        ResourceCreatedEvent resourceCreatedEvent = resourceMapper.resourceToResourceCreatedEvent(savedResource);
        resourceProducer.sendResourceCreated(resourceCreatedEvent);
        return resourceMapper.resourceModelToUploadResourceResponse(savedResource);
    }

    public GetResourceResponse getResource(@Valid @Positive Integer id) {
        Optional<Resource> resource = resourceRepository.findById(id);
        return resource
                .map(resourceClient::getResourceContent)
                .map(resourceMapper::resourceContentResponseToGetResourceResponse)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public DeleteResourceResponse deleteResources(List<Integer> ids) {
        List<Integer> deletedIds = ids.stream()
                .filter(this::deleteResourceIfPresent)
                .toList();

        resourceProducer.sendResourceDeleted(new ResourceDeletedEvent(deletedIds));

        return new DeleteResourceResponse(deletedIds);
    }

    private boolean deleteResourceIfPresent(Integer resourceId) {
        Optional<Resource> resource = resourceRepository.findById(resourceId);
        resource.ifPresent(resourceRepository::delete);
        return resource.isPresent();
    }
}