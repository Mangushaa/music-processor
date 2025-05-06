package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.example.dto.SongMetadata;
import org.example.intergration.client.SongClient;
import org.example.model.Resource;
import org.example.service.SongMetadataExtractor;
import org.example.service.SongService;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SongServiceImpl implements SongService {

    private static final String ERROR_UPLOADING_SONG_METADATA = "Error uploading song metadata: {}";

    private final SongClient songClient;

    private final SongMetadataExtractor songMetadataExtractor;

    @Override
    public void uploadSongMetadata(Resource resource) throws TikaException, IOException, SAXException {
        try {
            SongMetadata songMetadata = songMetadataExtractor.extractMetadata(resource.getContent());
            songMetadata.setId(resource.getId());
            songClient.createSongMetadata(songMetadata);
        } catch (RuntimeException | IOException | SAXException | TikaException e) {
            log.error(ERROR_UPLOADING_SONG_METADATA, e);
            throw e;
        }
    }

    @Override
    public void deleteSongMetadata(List<Integer> ids) {
        songClient.deleteSongsMetadata(ids);
    }
}
