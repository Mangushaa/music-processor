package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetSongMetadataResponse {
    private Integer id;
    private String name;
    private String artist;
    private String album;
    private String duration;
    private String year;
}
