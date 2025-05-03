package org.example.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.example.dto.CreateSongRequest;
import org.example.dto.CreateSongResponse;
import org.example.dto.DeleteSongsResponse;
import org.example.dto.GetSongResponse;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface SongService {

    CreateSongResponse createSong(@Valid CreateSongRequest createSongRequest);

    DeleteSongsResponse deleteSongs(@Valid @NotEmpty List<@Valid @Positive Integer> ids);

    GetSongResponse getSong(@Valid @Positive Integer id);
}
