package org.example.service.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException {

    private final Integer notFoundResourceId;

    public ResourceNotFoundException(Integer notFoundResourceId) {
        this.notFoundResourceId = notFoundResourceId;
    }
}
