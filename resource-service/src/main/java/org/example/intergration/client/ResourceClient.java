package org.example.intergration.client;

import org.example.intergration.client.dto.ResourceUploadResponseDto;

public interface ResourceClient {

    ResourceUploadResponseDto uploadResource(byte[] resource);
}
