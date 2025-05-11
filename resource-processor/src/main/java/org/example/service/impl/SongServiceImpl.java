package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.example.integration.client.ResourceServiceClient;
import org.example.integration.client.SongClient;
import org.example.integration.client.dto.SongMetadataDto;
import org.example.service.SongMetadataExtractor;
import org.example.service.SongService;
import org.example.service.exception.MetadataExtractingException;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private static final String ERROR_EXTRACTING_SONG_METADATA = "Error extracting song metadata: {}";

    private final SongClient songClient;

    private final SongMetadataExtractor songMetadataExtractor;

    private final ResourceServiceClient resourceServiceClient;

    @Override
    public void uploadSongMetadata(int resourceId) throws MetadataExtractingException {
        byte[] resource = resourceServiceClient.getResource(resourceId);
        SongMetadataDto songMetadata = extractSongMetadata(resource);
        songMetadata.setId(resourceId);
        songClient.loadSongMetadata(songMetadata);
    }

    @Override
    public void deleteSongMetadata(List<Integer> resourceIds) {
        songClient.deleteSongMetadata(resourceIds);
        log.info("Successfully deleted song metadata for resource IDs: {}", resourceIds);
    }

    private SongMetadataDto extractSongMetadata(byte[] resource) throws MetadataExtractingException {
        try {
            SongMetadataDto songMetadata = songMetadataExtractor.extractMetadata(resource);
            return songMetadata;
        } catch (RuntimeException | IOException | SAXException | TikaException e) {
            log.error(ERROR_EXTRACTING_SONG_METADATA, e);
            throw new MetadataExtractingException();
        }
    }
}
