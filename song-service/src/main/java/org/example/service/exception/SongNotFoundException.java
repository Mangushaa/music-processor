package org.example.service.exception;


import lombok.Getter;

public class SongNotFoundException extends RuntimeException {
    @Getter
    private int songId;

    public SongNotFoundException(Integer songId) {

    }
}
