package org.example.dto;

import lombok.Data;

@Data
public class SongMetadata {
    private String title;
    private String artist;
    private String album;
    private String genre;
    private int year;
    private String duration;
    private String bitrate;
    private String sampleRate;
    private String channels;
}
