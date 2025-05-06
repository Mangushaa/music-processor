package org.example.dto;

import lombok.Data;

@Data
public class SongMetadata {
    int id;
    private String name;
    private String artist;
    private String album;
    private String year;
    private String duration;
}
