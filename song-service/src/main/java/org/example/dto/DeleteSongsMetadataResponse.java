package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DeleteSongsMetadataResponse {

    @JsonProperty(value = "ids")
    private List<Integer> deletedSongIds;
}
