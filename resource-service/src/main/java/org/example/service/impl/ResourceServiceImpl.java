package org.example.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.dto.DeleteResourceResponse;
import org.example.dto.GetResourceResponse;
import org.example.dto.UploadResourceResponse;
import org.example.intergration.client.SongClient;
import org.example.mapper.ResourceMapper;
import org.example.model.Resource;
import org.example.repository.ResourceRepository;
import org.example.service.ResourceService;
import org.example.service.SongService;
import org.example.service.exception.ResourceNotFoundException;
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

    @Transactional
    public UploadResourceResponse uploadResource(@Valid @NotEmpty(message = "${validation.message.content.empty}") byte[] resource) {
        Resource newResource = resourceMapper.contentToResourceModel(resource);
        Resource savedResource = resourceRepository.save(newResource);
        songService.uploadSongMetadata(savedResource);
        return resourceMapper.resoucreModelToUploadResourceResponse(savedResource);
    }

    public GetResourceResponse getResource(@Valid @Positive Integer id) {
        Optional<Resource> resource = resourceRepository.findById(id);
        return resource.map(resourceMapper::resourceModelToGetResourceResponse).orElseThrow(() ->
                new ResourceNotFoundException(id));
    }

    @Transactional
    public DeleteResourceResponse deleteResources(@Valid @NotEmpty List<Integer> ids) {
        ids.forEach(this::deleteResoucreByIfPresent);
        return new DeleteResourceResponse(ids);
    }

    private void deleteResoucreByIfPresent(Integer id) {
        resourceRepository.findById(id).ifPresentOrElse(
                resourceRepository::delete
                , () -> {
                    throw new ResourceNotFoundException(id);
                });
    }
}