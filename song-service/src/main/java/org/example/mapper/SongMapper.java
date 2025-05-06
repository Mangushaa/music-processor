package org.example.mapper;

import org.example.dto.CreateSongMetadataRequest;
import org.example.dto.CreateSongMetadataResponse;
import org.example.dto.GetSongMetadataResponse;
import org.example.model.SongModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMapper {

    SongModel songCreateRequestToSongModel(CreateSongMetadataRequest createSongMetadataRequest);

    CreateSongMetadataResponse songModelToCreateSongResponse(SongModel songModel);

    GetSongMetadataResponse songModelToGetSongResponse(SongModel songModel);
}
