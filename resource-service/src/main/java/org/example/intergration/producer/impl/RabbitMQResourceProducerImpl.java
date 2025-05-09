package org.example.intergration.producer.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.configuration.properties.MessagingProperties;
import org.example.intergration.producer.ResourceProducer;
import org.example.intergration.producer.dto.ResourceCreatedEvent;
import org.example.intergration.producer.dto.ResourceDeletedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQResourceProducerImpl implements ResourceProducer {

    private static final String CREATE_RESOURCE_ROUTING_KEY = "resource.created";
    private static final String DELETE_RESOURCE_ROUTING_KEY = "resource.deleted";
    private final MessagingProperties messagingProperties;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendResourceCreated(ResourceCreatedEvent resourceCreatedEvent) {
        rabbitTemplate.convertAndSend(messagingProperties.getResourceExchange(), CREATE_RESOURCE_ROUTING_KEY, resourceCreatedEvent);
        log.info("Send resource created event to exchange: {}, routingKey: {}, event: {}", messagingProperties.getResourceExchange(), CREATE_RESOURCE_ROUTING_KEY, resourceCreatedEvent);
    }

    @Override
    public void sendResourceDeleted(ResourceDeletedEvent resourceDeletedEvent) {
        rabbitTemplate.convertAndSend(messagingProperties.getResourceExchange(), DELETE_RESOURCE_ROUTING_KEY, resourceDeletedEvent);
        log.info("Send resource deleted event to exchange: {}, routingKey: {}, event: {}", messagingProperties.getResourceExchange(), CREATE_RESOURCE_ROUTING_KEY, resourceDeletedEvent);
    }


}
