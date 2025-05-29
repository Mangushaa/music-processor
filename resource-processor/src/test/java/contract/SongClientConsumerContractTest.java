//package contract;
//
//import org.example.integration.client.SongClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
//import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//
//@SpringBootTest
//@AutoConfigureStubRunner(
//        ids = "org.example:song-service:+:stubs:8080",
//        stubsMode = StubRunnerProperties.StubsMode.LOCAL
//)
//public class SongClientConsumerContractTest {
//
//    @Autowired
//    private SongClient songClient;
//
//    @DynamicPropertySource
//    static void dynamicProperties(DynamicPropertyRegistry registry) {
//        registry.add("client.url.service.song", () -> "http://localhost:8080");
//    }
//
//    public void shouldReturnSongInformation() {
//        songClient.loadSongMetadata();
//    }
//
//
//
//}
