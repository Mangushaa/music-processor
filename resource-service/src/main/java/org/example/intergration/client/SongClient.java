package org.example.intergration.client;

import org.example.dto.SongMetadata;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${song-service.name}", url = "${song-service.url}")
public interface SongClient {

    @PostMapping("/v1/songs")
    void createSongMetadata(@RequestBody SongMetadata songMetadata);
}
