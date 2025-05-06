package org.example.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.dto.CreateSongMetadataRequest;
import org.example.dto.CreateSongMetadataResponse;
import org.example.dto.DeleteSongsMetadataResponse;
import org.example.dto.GetSongMetadataResponse;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface SongService {

    CreateSongMetadataResponse createSongMetadata(@Valid CreateSongMetadataRequest createSongMetadataRequest);

    DeleteSongsMetadataResponse deleteSongsMetadata(List<Integer> ids);

    GetSongMetadataResponse getSongMetadata(@Valid @Positive Integer id);
}
