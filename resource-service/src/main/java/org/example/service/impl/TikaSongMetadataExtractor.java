package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.example.dto.SongMetadata;
import org.example.metadataper.SongMetadataMapper;
import org.example.service.SongMetadataExtractor;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class TikaSongMetadataExtractor implements SongMetadataExtractor {

    private final SongMetadataMapper songMetadataMapper;

    @Override
    public SongMetadata extractMetadata(byte[] content) throws TikaException, IOException, SAXException {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        InputStream inputstream = new ByteArrayInputStream(content);
        ParseContext context = new ParseContext();
        Mp3Parser parser = new Mp3Parser();
        parser.parse(inputstream, handler, metadata, context);
        return songMetadataMapper.metadataToSongMetadata(metadata);
    }
}
