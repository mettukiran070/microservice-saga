package com.example.orderservice.command.api.controller;

import com.example.orderservice.command.api.command.CreateOrderCommand;
import com.example.orderservice.command.api.model.OrderDTO;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderCommandController {

  private CommandGateway commandGateway;

  public OrderCommandController(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @PostMapping("/order")
  public String createOrder(@RequestBody OrderDTO orderDTO) {
    String orderId = UUID.randomUUID().toString();
    CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
        .orderId(orderId)
        .addressId(orderDTO.getAddressId())
        .productId(orderDTO.getProductId())
        .quantity(orderDTO.getQuantity())
        .orderStatus("CREATED")
        .build();

    this.commandGateway.sendAndWait(createOrderCommand);
    return "Order created";
  }


}
