package org.example.metadataper;

import org.apache.tika.metadata.Metadata;
import org.example.dto.SongMetadata;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring")
public interface SongMetadataMapper {
    @Mapping(target = "artist", expression = "java(metadata.get(\"xmpDM:artist\"))")
    @Mapping(target = "album", expression = "java(metadata.get(\"xmpDM:album\"))")
    @Mapping(target = "name", expression = "java(metadata.get(\"dc:title\"))")
    @Mapping(target = "year", expression = "java(metadata.get(\"xmpDM:releaseDate\"))")
    @Mapping(target = "duration", expression = "java(metadata.get(\"xmpDM:duration\"))")
    SongMetadata metadataToSongMetadata(Metadata metadata);

    @AfterMapping
    default void formatDuration(Metadata metadata, @MappingTarget SongMetadata songMetadata) {
        BigDecimal duration = BigDecimal.valueOf(Double.parseDouble(songMetadata.getDuration()));
        BigDecimal minutes = duration.setScale(0, RoundingMode.DOWN);
        songMetadata.setDuration(String.format("%02d:%02d", minutes.intValue(), getSeconds(duration, minutes)));
    }


    default int getSeconds(BigDecimal duration, BigDecimal minutes) {
        return duration.subtract(minutes).multiply(BigDecimal.valueOf(10)).intValue();
    }
}
