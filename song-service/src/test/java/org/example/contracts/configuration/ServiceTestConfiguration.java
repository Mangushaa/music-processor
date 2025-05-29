package org.example.contracts.configuration;

import org.example.service.SongService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class ServiceTestConfiguration {

    @Bean
    @Primary
    public SongService songService() {
        return Mockito.mock(SongService.class);
    }

}
