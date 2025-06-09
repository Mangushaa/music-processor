package org.example;

import org.example.integration.client.SongClient;
import org.example.integration.client.dto.SongMetadataDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
        "eureka.client.enabled=false"
})
@AutoConfigureStubRunner(
        ids = "org.example:song-service:+:stubs:8081",
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
public class SongClientConsumerContractTest {

    private static final int SONG_ID = 1;

    @Autowired
    private SongClient songClient;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("client.url.service.song", () -> "http://localhost:8081");
    }

    @Test
    public void shouldReturnSongInformation() {
        SongMetadataDto songMetadata = songClient.getSongMetadata(SONG_ID);
        assertThat(songMetadata).isNotNull();
        assertThat(songMetadata.getId()).isEqualTo(SONG_ID);
        assertThat(songMetadata.getName()).isEqualTo("Numb");
        assertThat(songMetadata.getArtist()).isEqualTo("Linkin Park");
        assertThat(songMetadata.getAlbum()).isEqualTo("Meteora");
        assertThat(songMetadata.getDuration()).isEqualTo("03:07");
        assertThat(songMetadata.getYear()).isEqualTo("2003");
    }

    @Test
    public void shouldUploadSongMetadata() {
        Integer createdSongId = songClient.loadSongMetadata(songMetadataDto());
        assertThat(createdSongId).isEqualTo(SONG_ID);
    }

    @Test
    public void deleteSongMetadata() {
        songClient.deleteSongMetadata(List.of(SONG_ID));
    }

    private SongMetadataDto songMetadataDto() {
        return SongMetadataDto.builder()
                .id(1)
                .name("Numb")
                .artist("Linkin Park")
                .album("Meteora")
                .duration("03:07")
                .year("2003")
                .build();
    }
}
