package org.example.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.CreateSongRequest;
import org.example.dto.CreateSongResponse;
import org.example.dto.DeleteSongsResponse;
import org.example.dto.GetSongResponse;
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

    private SongRepository songRepository;

    private SongMapper songMapper;

    @Override
    public CreateSongResponse createSong(@Valid CreateSongRequest createSongRequest) {
        SongModel newSong = songMapper.songCreateRequestToSongModel(createSongRequest);
        SongModel savedSong = songRepository.save(newSong);
        return songMapper.songModelToCreateSongResponse(savedSong);
    }

    @Override
    public DeleteSongsResponse deleteSongs(List<@Valid @ExistedSongId Integer> ids) {
        List<SongModel> songsToDelete = songRepository.findAllById(ids);
        songRepository.deleteAll(songsToDelete);
        return new DeleteSongsResponse(ids);
    }

    @Override
    public GetSongResponse getSong(Integer songId) {
        return songRepository.findById(songId)
                .map(songMapper::songModelToGetSongResponse)
                .orElseThrow(() -> new SongNotFoundException(songId));
    }
}
