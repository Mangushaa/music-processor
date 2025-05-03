package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.example.dto.SongMetadata;
import org.example.mapper.SongMetadataMapper;
import org.example.service.SongMetadataExtractor;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class TikaSongMetadataExtractor implements SongMetadataExtractor {

    private final SongMetadataMapper songMetadataMapper;

    @Override
    public SongMetadata extractMetadata(byte[] content) throws TikaException, IOException, SAXException {
//        Metadata metadata = new Metadata();
//        BodyContentHandler handler = new BodyContentHandler();
//        ParseContext pcontext = new ParseContext();
//        Parser parser = new AutoDetectParser();
//        InputStream inputStream = new ByteArrayInputStream(content);
//        parser.parse(inputStream, handler, metadata, pcontext);
        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        InputStream inputstream = new ByteArrayInputStream(content);
        ParseContext context = new ParseContext();

        parser.parse(inputstream, handler, metadata, context);
        System.out.println(handler.toString());

        //getting the list of all meta data elements
        String[] metadataNames = metadata.names();

        for(String name : metadataNames) {
            System.out.println(name + ": " + metadata.get(name));
        }
        return songMetadataMapper.mapToMetadata(metadata);
    }
}
