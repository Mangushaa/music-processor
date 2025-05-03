package org.example.service;

import org.apache.tika.exception.TikaException;
import org.example.dto.SongMetadata;
import org.xml.sax.SAXException;

import java.io.IOException;

public interface SongMetadataExtractor {
    SongMetadata extractMetadata(byte[] content) throws TikaException, IOException, SAXException;
}
