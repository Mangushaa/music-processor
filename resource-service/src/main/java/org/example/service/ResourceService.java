package org.example.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.example.dto.DeleteResourceResponse;
import org.example.dto.GetResourceResponse;
import org.example.dto.UploadResourceResponse;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface ResourceService {

    UploadResourceResponse uploadResource(
            @Valid @NotEmpty(message = "${validation.message.content.empty}") byte[] resource);

    GetResourceResponse getResource(@Valid @Positive Integer id);

    DeleteResourceResponse deleteResources(@Valid @NotEmpty List<Integer> id);
}
