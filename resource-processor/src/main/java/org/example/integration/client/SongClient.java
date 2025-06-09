package org.example.integration.client;

import feign.FeignException;
import org.example.integration.client.dto.SongMetadataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Retryable(
        include = {FeignException.GatewayTimeout.class},
        backoff = @Backoff(delay = 2000))
@FeignClient(name = "song-service", url = "${clients.url.service.song}")
public interface SongClient {

    @PostMapping("/songs")
    Integer loadSongMetadata(SongMetadataDto songMetadataDto);

    @DeleteMapping("/songs")
    void deleteSongMetadata(@RequestParam(value = "id") List<Integer> ids);

    @GetMapping(path = "/songs/{id}", produces = "application/json")
    SongMetadataDto getSongMetadata(@PathVariable(value = "id") Integer id);

}
