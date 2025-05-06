package org.example.service.impl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.CreateSongMetadataRequest;
import org.example.dto.CreateSongMetadataResponse;
import org.example.dto.DeleteSongsMetadataResponse;
import org.example.dto.GetSongMetadataResponse;
import org.example.mapper.SongMapper;
import org.example.model.SongModel;
import org.example.repository.SongRepository;
import org.example.service.SongService;
import org.example.service.exception.SongMetadataAlreadyExistsException;
import org.example.service.exception.SongNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Validated
@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;

    private final SongMapper songMapper;

    @Override
    public CreateSongMetadataResponse createSongMetadata(@Valid CreateSongMetadataRequest createSongMetadataRequest) {
        if (songRepository.existsById(createSongMetadataRequest.getId())) {
            throw new SongMetadataAlreadyExistsException(createSongMetadataRequest.getId());
        }
        SongModel newSong = songMapper.songCreateRequestToSongModel(createSongMetadataRequest);
        SongModel savedSong = songRepository.save(newSong);
        return songMapper.songModelToCreateSongResponse(savedSong);
    }

    @Override
    public DeleteSongsMetadataResponse deleteSongsMetadata(List<Integer> ids) {
        List<Integer> deletedIds = ids.stream()
                .filter(this::deleteSongByIdIfPresent)
                .toList();

        return new DeleteSongsMetadataResponse(deletedIds);
    }

    @Override
    public GetSongMetadataResponse getSongMetadata(Integer songId) {
        return songRepository.findById(songId)
                .map(songMapper::songModelToGetSongResponse)
                .orElseThrow(() -> new SongNotFoundException(songId));
    }

    private boolean deleteSongByIdIfPresent(Integer resourceId) {
        Optional<SongModel> songMetadata = songRepository.findById(resourceId);
        songMetadata.ifPresent(songRepository::delete);
        return songMetadata.isPresent();
    }
}
