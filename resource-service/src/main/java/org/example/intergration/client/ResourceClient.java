package org.example.intergration.client;

import org.example.intergration.client.dto.ResourceContentResponse;
import org.example.intergration.client.dto.ResourceUploadResponse;
import org.example.model.Resource;

public interface ResourceClient {

    ResourceUploadResponse uploadResource(byte[] resource);

    ResourceContentResponse getResourceContent(Resource resource);
}
