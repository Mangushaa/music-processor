package org.example.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.example.dto.CreateSongMetadataRequest;
import org.example.dto.CreateSongMetadataResponse;
import org.example.dto.DeleteSongsMetadataResponse;
import org.example.dto.GetSongMetadataResponse;
import org.example.validation.annotation.ExistedSongId;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface SongService {

    CreateSongMetadataResponse createSongMetadata(@Valid CreateSongMetadataRequest createSongMetadataRequest);

    DeleteSongsMetadataResponse deleteSongsMetadata(@Size(min = 1, max = 200) List<@ExistedSongId Integer> ids);

    GetSongMetadataResponse getSongMetadata(@Valid @Positive Integer id);
}
