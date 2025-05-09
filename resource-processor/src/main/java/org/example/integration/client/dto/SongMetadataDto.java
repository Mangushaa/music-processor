package org.example.integration.client.dto;

import lombok.Data;

@Data
public class SongMetadataDto {
    int id;
    private String name;
    private String artist;
    private String album;
    private String year;
    private String duration;
}
