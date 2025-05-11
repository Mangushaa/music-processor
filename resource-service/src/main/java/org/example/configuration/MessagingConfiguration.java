package org.example.configuration;

import lombok.RequiredArgsConstructor;
import org.example.configuration.properties.MessagingProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class MessagingConfiguration {

    private static final String RESOURCE_CREATED_ROUTING_KEY = "resource.created";
    private static final String RESOURCE_DELETED_ROUTING_KEY = "resource.deleted";

    private final MessagingProperties messagingProperties;

    @Bean
    @Qualifier("resourceDeleteQueue")
    public Queue resourceDeletedQueue() {
        return new Queue(messagingProperties.getResourceDeletedQueueName(), false);
    }

    @Bean
    @Qualifier("resourceCreatedQueue")
    public Queue resourceCreatedQueue() {
        return new Queue(messagingProperties.getResourceCreatedQueueName(), false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(messagingProperties.getResourceExchange());
    }

    @Bean
    public Binding resourceExchangeToResourceCreatedQueueBonding(@Qualifier("resourceCreatedQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RESOURCE_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding resourceExchangeToResourceDeletedQueueBonding(@Qualifier("resourceDeletedQueue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RESOURCE_DELETED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
