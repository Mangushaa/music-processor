package org.example.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class GetSongResponse {

    @Positive
    private Integer id;
    @Length(min = 1, max = 100)
    private String name;
    @Length(min = 1, max = 100)
    private String artist;
    @Length(min = 1, max = 100)
    private String album;

    //TODO : Add a custom validator for the duration format
    private String duration;

    @DateTimeFormat(pattern = "YYYY")
    private String year;

}
