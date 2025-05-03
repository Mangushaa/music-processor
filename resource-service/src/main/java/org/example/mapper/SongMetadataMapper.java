package org.example.mapper;

import org.apache.tika.metadata.Metadata;
import org.example.dto.SongMetadata;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SongMetadataMapper {
    SongMetadata mapToMetadata(Metadata metadata);
}
