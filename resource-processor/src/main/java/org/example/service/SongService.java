package org.example.service;

import org.example.service.exception.MetadataExtractingException;

import java.util.List;

public interface SongService {
    void uploadSongMetadata(int resourceId) throws MetadataExtractingException;

    void deleteSongMetadata(List<Integer> resourceIds);
}
