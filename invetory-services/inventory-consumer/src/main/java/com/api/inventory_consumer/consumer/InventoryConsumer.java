package com.api.inventory_consumer.consumer;


import com.rabbitmq.client.Channel;
import constants.RabbitMQConstants;
import dto.InventoryDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class InventoryConsumer {

    @RabbitListener(queues = RabbitMQConstants.INVENTORY_QUEUE)
    private void listen(InventoryDto dto, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG)  long tag) throws IOException {
        try {
            System.out.printf("product code %s \n", dto.productCode);
            System.out.printf("quantity %s \n\n", dto.quantity);

            if (dto.quantity <= 0) {
                System.out.println("ACK");
                channel.basicReject(tag, false);
            } else {
                System.out.println("NACK");
                channel.basicAck(tag, false);
            }
        } catch (Exception e) {
            channel.basicReject(tag, true);
        }
    }
}
