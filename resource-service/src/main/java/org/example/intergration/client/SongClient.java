package org.example.intergration.client;

import org.example.dto.SongMetadata;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "${song-service.name}")
public interface SongClient {

    @PostMapping("/songs")
    void createSongMetadata(@RequestBody SongMetadata songMetadata);

    @DeleteMapping("/songs")
    void deleteSongsMetadata(@RequestParam("id") List<Integer> ids);
}
