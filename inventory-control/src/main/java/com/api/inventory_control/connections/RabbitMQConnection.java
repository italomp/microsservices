package com.api.inventory_control.connections;

import constants.RabbitMQConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConnection {
    private final String EXCHANGE_NAME = "amq.direct";
    private final  AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String name) {
        return new Queue(name, true, false, false);
    }

    private DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    private Binding bind(Queue queue, DirectExchange exchange) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void add() {
        Queue inventoryQueue = this.queue(RabbitMQConstants.INVENTORY_QUEUE);
        Queue priceQueue = this.queue(RabbitMQConstants.PRICE_QUEUE);

        DirectExchange exchange = this.directExchange();

        Binding inventoryBinding = this.bind(inventoryQueue, exchange);
        Binding priceBinding = this.bind(priceQueue, exchange);

        this.amqpAdmin.declareQueue(inventoryQueue);
        this.amqpAdmin.declareQueue(priceQueue);

        this.amqpAdmin.declareExchange(exchange);

        this.amqpAdmin.declareBinding(inventoryBinding);
        this.amqpAdmin.declareBinding(priceBinding);
    }
}
