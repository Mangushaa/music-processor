package org.example.service;

import org.apache.tika.exception.TikaException;
import org.example.model.Resource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

public interface SongService {
    void uploadSongMetadata(Resource resource) throws TikaException, IOException, SAXException;
    void deleteSongMetadata(List<Integer> id);
}
