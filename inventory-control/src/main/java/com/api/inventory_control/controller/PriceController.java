package com.api.inventory_control.controller;

import com.api.inventory_control.service.RabbitMQService;
import constants.RabbitMQConstants;
import dto.PriceDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("price")
public class PriceController {

    private RabbitMQService rabbitMQService;

    public PriceController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @PutMapping
    private ResponseEntity updatePrice(@RequestBody PriceDto dto) {
        this.rabbitMQService.sendMessage(RabbitMQConstants.PRICE_QUEUE, dto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
