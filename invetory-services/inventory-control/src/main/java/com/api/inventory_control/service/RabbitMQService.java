package com.api.inventory_control.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {

    private RabbitTemplate rabbitTemplate;

    private final String EXCHANGE_NAME = "inventory.direct";

    public RabbitMQService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String queueName, Object message) {
        this.rabbitTemplate.convertAndSend(EXCHANGE_NAME, queueName, message);
    }
}
