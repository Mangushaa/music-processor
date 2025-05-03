package org.example.api.v1;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.example.dto.DeleteResourceResponse;
import org.example.dto.GetResourceResponse;
import org.example.dto.UploadResourceResponse;
import org.example.service.ResourceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping(consumes = "audio/mpeg", produces = "application/json")
    public ResponseEntity<UploadResourceResponse> uploadResource(@RequestBody byte[] content) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resourceService.uploadResource(content));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetResourceResponse> getResource(@PathParam(value = "id") Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resourceService.getResource(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResourceResponse> deleteResources(@RequestParam(value = "ids") List<Integer> idsToRemove) {
        System.out.println("Received request to delete resources with ids: " + idsToRemove);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(resourceService.deleteResources(idsToRemove));
    }
}
