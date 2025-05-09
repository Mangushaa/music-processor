package org.example.configuration;

import lombok.RequiredArgsConstructor;
import org.example.configuration.properties.MessagingProperties;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;

@RequiredArgsConstructor
@Configuration
public class MessagingConfiguration {

    private final MessagingProperties messagingProperties;

    @Autowired
    private ApplicationContext applicationContext;

//    @Autowired
//    private PropertySource propertySource;

    @Bean
    @Qualifier("resourceDeleteQueue")
    public Queue resourceDeletedQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(messagingProperties.getResourceDeletedQueueName(), false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    @Qualifier("resourceCreatedQueue")
    public Queue resourceCreatedQueue(RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(messagingProperties.getResourceCreatedQueueName(), false);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public TopicExchange exchange(RabbitAdmin rabbitAdmin) {
        TopicExchange topicExchange = new TopicExchange(messagingProperties.getResourceExchange());
        rabbitAdmin.declareExchange(topicExchange);
        return topicExchange;
    }

    @Bean
    public Binding resourceExchangeToResourceCreatedQueueBonding(@Qualifier("resourceCreatedQueue") Queue queue, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(messagingProperties.getResourceCreatedRoutingKey());
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    public Binding resourceExchangeToResourceDeletedQueueBonding(@Qualifier("resourceDeletedQueue") Queue queue, TopicExchange exchange, RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(messagingProperties.getResourceDeletedRoutingKey());
        rabbitAdmin.declareBinding(binding);
        return binding;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
