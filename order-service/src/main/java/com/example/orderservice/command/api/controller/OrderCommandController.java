package com.example.orderservice.command.api.controller;

import com.example.orderservice.command.api.model.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderCommandController {

  @PostMapping("/order")
  public String createOrder(@RequestBody OrderDTO orderDTO) {
    return null;
  }



}
