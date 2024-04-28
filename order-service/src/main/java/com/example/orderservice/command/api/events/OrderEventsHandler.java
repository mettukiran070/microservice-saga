package com.example.orderservice.command.api.events;

import com.example.orderservice.command.api.entity.Order;
import com.example.orderservice.command.api.repository.OrderRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class OrderEventsHandler {

  private final OrderRepository orderRepository;

  public OrderEventsHandler(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  @EventHandler
  public void on(OrderCreatedEvent orderCreatedEvent) {
    Order order = new Order();
    BeanUtils.copyProperties(orderCreatedEvent, order);
    orderRepository.save(order);
  }

}
