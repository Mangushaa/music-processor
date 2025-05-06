package org.example.api.v1;

import lombok.RequiredArgsConstructor;
import org.example.dto.CreateSongMetadataRequest;
import org.example.dto.CreateSongMetadataResponse;
import org.example.dto.DeleteSongsMetadataResponse;
import org.example.dto.GetSongMetadataResponse;
import org.example.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
public class SongMetadataController {

    private final SongService songService;

    @PostMapping
    public ResponseEntity<CreateSongMetadataResponse> createSongMetadata(@RequestBody CreateSongMetadataRequest createSongMetadataRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(songService.createSongMetadata(createSongMetadataRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongMetadataResponse> getSongMetadata(@PathVariable(value = "id") Integer id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(songService.getSongMetadata(id));
    }

    @DeleteMapping
    public ResponseEntity<DeleteSongsMetadataResponse> deleteSongsMetadata(@RequestParam(value = "ids") List<Integer> ids) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(songService.deleteSongsMetadata(ids));
    }
}
