package org.example.service;

import jakarta.validation.Valid;
import org.example.model.Resource;
import org.example.service.exception.MetadataExtractingException;

import java.util.List;

public interface SongService {
    void uploadSongMetadata(byte[] bytes, Resource resource) throws MetadataExtractingException;
    void deleteSongMetadata(List<Integer> id);
}
