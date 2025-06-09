package org.example.integration.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongMetadataDto {
    int id;
    private String name;
    private String artist;
    private String album;
    private String year;
    private String duration;
}
