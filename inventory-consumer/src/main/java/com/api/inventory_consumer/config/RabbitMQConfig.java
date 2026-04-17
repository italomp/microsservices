package com.api.inventory_consumer.config;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class RabbitMQConfig {
    /**
     * Defines how messages are serialized/deserialized (Java, JSON, etc.).
     * Always needed when you want non-default behavior. If you don't declare it,
     * Spring uses SimpleMessageConverter without any trusted packages
     * @return
     */
    @Bean
    public MessageConverter converter() {
        SimpleMessageConverter converter =  new SimpleMessageConverter();
        converter.setAllowedListPatterns(List.of("dto.*"));
        return converter;
    }
}
