package org.example.service.exception;

import lombok.Getter;

public class SongMetadataAlreadyExistsException extends RuntimeException {

    @Getter
    private int songMetadataId;

    public SongMetadataAlreadyExistsException(int songMetadataId) {
        this.songMetadataId = songMetadataId;
    }
}
