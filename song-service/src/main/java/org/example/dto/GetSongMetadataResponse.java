package org.example.dto;

import lombok.Data;

@Data
public class GetSongMetadataResponse {
    private Integer id;
    private String name;
    private String artist;
    private String album;
    private String duration;
    private String year;
}
