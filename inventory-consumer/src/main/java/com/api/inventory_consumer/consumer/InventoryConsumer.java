package com.api.inventory_consumer.consumer;


import constants.RabbitMQConstants;
import dto.InventoryDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryConsumer {

    @RabbitListener(queues = RabbitMQConstants.INVENTORY_QUEUE)
    private void listen(InventoryDto dto) {
        System.out.printf("product code %s \n", dto.productCode);
        System.out.printf("quantity %s \n\n", dto.quantity);
    }
}
