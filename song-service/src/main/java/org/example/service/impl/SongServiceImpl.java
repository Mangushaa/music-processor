package org.example.service.impl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.example.dto.CreateSongMetadataRequest;
import org.example.dto.CreateSongMetadataResponse;
import org.example.dto.DeleteSongsMetadataResponse;
import org.example.dto.GetSongMetadataResponse;
import org.example.mapper.SongMapper;
import org.example.model.SongModel;
import org.example.repository.SongRepository;
import org.example.service.SongService;
import org.example.service.exception.SongNotFoundException;
import org.example.validation.annotation.ExistedSongId;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Validated
@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    private final SongMapper songMapper;

    @Override
    public CreateSongMetadataResponse createSongMetadata(@Valid CreateSongMetadataRequest createSongMetadataRequest) {
        SongModel newSong = songMapper.songCreateRequestToSongModel(createSongMetadataRequest);
        SongModel savedSong = songRepository.save(newSong);
        return songMapper.songModelToCreateSongResponse(savedSong);
    }

    @Override
    public DeleteSongsMetadataResponse deleteSongsMetadata(@Size(min = 1, max = 200) List<@ExistedSongId Integer> ids) {
        List<SongModel> songsToDelete = songRepository.findAllById(ids);
        songRepository.deleteAll(songsToDelete);
        return new DeleteSongsMetadataResponse(ids);
    }

    @Override
    public GetSongMetadataResponse getSongMetadata(Integer songId) {
        return songRepository.findById(songId)
                .map(songMapper::songModelToGetSongResponse)
                .orElseThrow(() -> new SongNotFoundException(songId));
    }
}
