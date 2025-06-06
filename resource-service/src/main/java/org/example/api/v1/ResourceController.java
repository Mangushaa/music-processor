package org.example.api.v1;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.example.dto.DeleteResourceResponse;
import org.example.dto.GetResourceResponse;
import org.example.dto.UploadResourceResponse;
import org.example.service.ResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<UploadResourceResponse> uploadResource(@RequestBody byte[] content) throws TikaException, IOException, SAXException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resourceService.uploadResource(content));
    }

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    public ResponseEntity<byte[]> getResource(@PathVariable(value = "id") Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resourceService.getResource(id).getContent());
    }

    @DeleteMapping
    public ResponseEntity<DeleteResourceResponse> deleteResources(@RequestParam(value = "id") List<Integer> idsToRemove) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resourceService.deleteResources(idsToRemove));
    }
}
