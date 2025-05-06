package org.example.service;

import org.apache.tika.exception.TikaException;
import org.example.model.Resource;
import org.example.service.exception.MetadataExtractingException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

public interface SongService {
    void uploadSongMetadata(Resource resource) throws MetadataExtractingException;
    void deleteSongMetadata(List<Integer> id);
}
