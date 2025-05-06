package org.example.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateSongMetadataRequest {

    @Positive(message = "Id must be positive")
    private Integer id;
    @NotEmpty
    @Length(min = 1, max = 100, message = "Song name must be between 1 and 100 characters")
    private String name;
    @NotEmpty
    @Length(min = 1, max = 100, message = "Artist name must be between 1 and 100 characters")
    private String artist;
    @NotEmpty
    @Length(min = 1, max = 100, message = "Album name must be between 1 and 100 characters")
    private String album;
    @Pattern(regexp = "^[0-5]\\d{1}:[0-5]\\d{1}$", message = "Duration must be in the format mm:ss")
    private String duration;
    @Pattern(regexp = "^(19\\d{2}|2\\d{3})$", message = "Year must be between 1900 and 2999")
    private String year;
}
