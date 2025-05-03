package org.example.mapper;

import org.example.dto.CreateSongRequest;
import org.example.dto.CreateSongResponse;
import org.example.dto.GetSongResponse;
import org.example.model.SongModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {

    SongModel songCreateRequestToSongModel(CreateSongRequest createSongRequest);

    CreateSongResponse songModelToCreateSongResponse(SongModel songModel);

    GetSongResponse songModelToGetSongResponse(SongModel songModel);
}
