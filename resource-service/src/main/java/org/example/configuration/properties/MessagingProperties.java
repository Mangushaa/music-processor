package org.example.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "messaging.rabbitmq")
public class MessagingProperties {

    private String resourceCreatedQueueName;
    private String resourceDeletedQueueName;
    private String resourceExchange;
    private String resourceCreatedRoutingKey;
    private String resourceDeletedRoutingKey;


}
