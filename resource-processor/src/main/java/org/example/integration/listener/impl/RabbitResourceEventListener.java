package org.example.integration.listener.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.integration.listener.ResourceEventListener;
import org.example.integration.listener.event.ResourceCreatedEvent;
import org.example.integration.listener.event.ResourceDeletedEvent;
import org.example.service.SongService;
import org.example.service.exception.MetadataExtractingException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitResourceEventListener implements ResourceEventListener {
    private final ObjectMapper objectMapper;

    private final SongService songService;

    @RabbitListener(queues = "${messaging.rabbitmq.resourceCreatedQueueName}")
    public void handleResourceCreated(byte[] message) {
        ResourceCreatedEvent resourceCreatedEvent = null;
        try {
            resourceCreatedEvent = objectMapper.readValue(message, ResourceCreatedEvent.class);
            log.info("Processing resource created event: ResourceEvent: {}:", resourceCreatedEvent);
            songService.uploadSongMetadata(resourceCreatedEvent.getResourceId());
            log.info("Successfully processed resource creation. ResourceEvent: {}:", resourceCreatedEvent);
        } catch (IOException e) {
            log.error("Error converting resource created event: {}", resourceCreatedEvent);
        } catch (MetadataExtractingException e) {
            log.error("Error metadata extracting event: {}", resourceCreatedEvent);
        }
    }

    @RabbitListener(queues = "${messaging.rabbitmq.resourceDeletedQueueName}")
    public void handleResourceDeleted(byte[] message) {
        ResourceDeletedEvent resourceDeletedEvent = null;
        try {
            resourceDeletedEvent = objectMapper.readValue(message, ResourceDeletedEvent.class);
            log.info("Processing resource:" + resourceDeletedEvent);
            songService.deleteSongMetadata(resourceDeletedEvent.getResourceIds());
            log.info("Successfully processed delete resources. Event: {}", resourceDeletedEvent);
        } catch (IOException e) {
            log.error("Error converting resource delete event: {}", resourceDeletedEvent);
        }
    }
}
