package com.api.inventory_control.connections;

import constants.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConnection {
    private final String EXCHANGE_NAME = "inventory.direct";

    @Bean
    public Queue inventoryQueue() {
        return QueueBuilder.durable(RabbitMQConstants.INVENTORY_QUEUE).build();
    }

    @Bean
    public Queue priceQueue() {
        return QueueBuilder.durable(RabbitMQConstants.PRICE_QUEUE).build();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding inventoryBind(Queue inventoryQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(inventoryQueue).to(directExchange).with(RabbitMQConstants.INVENTORY_QUEUE);
    }

    @Bean
    public Binding priceBind(Queue priceQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(priceQueue).to(directExchange).with(RabbitMQConstants.PRICE_QUEUE);
    }

    // Bean to create structures (queues, exchanges, etc) in rabbitMQ
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public ApplicationRunner rabbitInit(RabbitAdmin rabbitAdmin) {
        return args -> rabbitAdmin.initialize();
    }
}
