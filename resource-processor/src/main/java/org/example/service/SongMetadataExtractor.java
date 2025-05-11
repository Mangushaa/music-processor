package org.example.service;

import org.apache.tika.exception.TikaException;
import org.example.integration.client.dto.SongMetadataDto;
import org.xml.sax.SAXException;

import java.io.IOException;

public interface SongMetadataExtractor {
    SongMetadataDto extractMetadata(byte[] content) throws TikaException, IOException, SAXException;
}
