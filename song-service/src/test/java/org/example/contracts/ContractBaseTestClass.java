package org.example.contracts;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.example.api.v1.SongMetadataController;
import org.example.contracts.configuration.ServiceTestConfiguration;
import org.example.dto.CreateSongMetadataResponse;
import org.example.dto.DeleteSongsMetadataResponse;
import org.example.dto.GetSongMetadataResponse;
import org.example.service.SongService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = {
        "eureka.client.enabled=false",
        "eureka.client.register-with-eureka=false",
        "eureka.client.fetch-registry=false"
})
@DirtiesContext
@AutoConfigureMessageVerifier
@Import(ServiceTestConfiguration.class)
public class ContractBaseTestClass {

    private static final int SONG_METADATA_ID = 1;

    @Autowired
    private SongService songService;

    @Autowired
    private SongMetadataController songMetadataController;

    @BeforeEach
    public void setup() {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder
                = MockMvcBuilders.standaloneSetup(songMetadataController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);

        when(songService.getSongMetadata(SONG_METADATA_ID)).thenReturn(
                new GetSongMetadataResponse(1, "Test Song", "Test Artist", "Test Album", "07:12", "2001")
        );

        when(songService.deleteSongsMetadata(any())).thenAnswer(invocationOnMock ->
                new DeleteSongsMetadataResponse(
                        invocationOnMock.getArgument(0)
                )
        );

        when(songService.createSongMetadata(any())).thenReturn(new CreateSongMetadataResponse(SONG_METADATA_ID));
    }
}
