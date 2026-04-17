package com.api.inventory_control.controller;

import com.api.inventory_control.service.RabbitMQService;
import constants.RabbitMQConstants;
import dto.InventoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("inventory")
public class InventoryController {
    private RabbitMQService rabbitMQService;

    public InventoryController(RabbitMQService rabbitMQService) {
        this.rabbitMQService = rabbitMQService;
    }

    @PutMapping
    private ResponseEntity updateInventory(@RequestBody InventoryDto dto) {
        this.rabbitMQService.sendMessage(RabbitMQConstants.INVENTORY_QUEUE, dto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
