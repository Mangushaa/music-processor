package org.example.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.dto.DeleteResourceResponse;
import org.example.dto.GetResourceResponse;
import org.example.dto.UploadResourceResponse;
import org.example.validation.annotation.GivenMimeType;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ResourceService {

    UploadResourceResponse uploadResource(
            @Valid @GivenMimeType(mimeType = "audio/mpeg") byte[] content);

    GetResourceResponse getResource(@Valid @Positive Integer id);

    DeleteResourceResponse deleteResources(List<Integer> ids);
}
