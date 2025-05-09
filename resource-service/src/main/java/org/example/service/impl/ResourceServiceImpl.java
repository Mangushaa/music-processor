package org.example.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.dto.DeleteResourceResponse;
import org.example.dto.GetResourceResponse;
import org.example.dto.UploadResourceResponse;
import org.example.intergration.client.ResourceClient;
import org.example.intergration.client.dto.ResourceUploadResponseDto;
import org.example.mapper.ResourceMapper;
import org.example.model.Resource;
import org.example.repository.ResourceRepository;
import org.example.service.ResourceService;
import org.example.service.SongService;
import org.example.service.exception.MetadataExtractingException;
import org.example.service.exception.ResourceNotFoundException;
import org.example.validation.annotation.GivenMimeType;
import org.springframework.cache.annotation.Cacheable;
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

    private final SongService songService;

    private final ResourceClient resourceClient;

    @Transactional(rollbackFor = Exception.class)
    public UploadResourceResponse uploadResource(
            @Valid @GivenMimeType(mimeType = "audio/mpeg") byte[] resource
    ) throws MetadataExtractingException {
        ResourceUploadResponseDto resourceUploadResponseDto = resourceClient.uploadResource(resource);
        Resource newResource = resourceMapper.resourceUploadResponseDtoToResource(resourceUploadResponseDto);
        Resource savedResource = resourceRepository.save(newResource);
        songService.uploadSongMetadata(resource, savedResource);
        return resourceMapper.resoucreModelToUploadResourceResponse(savedResource);
    }

    public GetResourceResponse getResource(@Valid @Positive Integer id) {
        Optional<Resource> resource = resourceRepository.findById(id);
        return resource.map(resourceMapper::resourceModelToGetResourceResponse).orElseThrow(() ->
                new ResourceNotFoundException(id));
    }

    @Transactional
    public DeleteResourceResponse deleteResources(List<Integer> ids) {
        List<Integer> deletedIds = ids.stream()
                .filter(this::deleteResourceIfPresent)
                .toList();

        if (!deletedIds.isEmpty()) {
            songService.deleteSongMetadata(deletedIds);
        }
        return new DeleteResourceResponse(deletedIds);
    }

    private boolean deleteResourceIfPresent(Integer resourceId) {
        Optional<Resource> resource = resourceRepository.findById(resourceId);
        resource.ifPresent(resourceRepository::delete);
        return resource.isPresent();
    }
}