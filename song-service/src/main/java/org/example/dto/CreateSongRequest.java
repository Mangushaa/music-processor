package org.example.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.example.validation.annotation.NotExistedSongId;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class CreateSongRequest {

    @Positive
    @NotExistedSongId
    private Integer id;
    @Length(min = 1, max = 100)
    private String name;
    @Length(min = 1, max = 100)
    private String artist;
    @Length(min = 1, max = 100)
    private String album;

    @Pattern(regexp = "^\\d{2}:\\d{2}$")
    private String duration;

    @DateTimeFormat(pattern = "YYYY")
    private String year;

}
