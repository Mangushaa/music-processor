package org.example.api.v1;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateSongRequest;
import org.example.dto.CreateSongResponse;
import org.example.dto.DeleteSongsResponse;
import org.example.dto.GetSongResponse;
import org.example.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/songs")
public class SongController {

    private SongService songService;

    @PostMapping
    public ResponseEntity<CreateSongResponse> createSongMetadata(@RequestBody CreateSongRequest createSongRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(songService.createSong(createSongRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponse> getSongMetadata(@PathVariable(value = "id") Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(songService.getSong(id));
    }

    @DeleteMapping("/{ids}")
    public ResponseEntity<DeleteSongsResponse> deleteSongsMetadata(@PathVariable(value = "ids") List<Integer> ids) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(songService.deleteSongs(ids));
    }
}
