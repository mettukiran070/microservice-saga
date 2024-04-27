package com.example.orderservice.command.api.aggregate;

import com.example.orderservice.command.api.command.CreateOrderCommand;
import com.example.orderservice.command.api.events.OrderCreatedEvent;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class OrderAggregate {

  @AggregateIdentifier
  private String orderId;
  private String productId;
  private String userId;
  private String addressId;
  private Integer quantity;
  private String orderStatus;

  public OrderAggregate() {}

  public OrderAggregate(CreateOrderCommand createOrderCommand) {
    OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
    BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
    AggregateLifecycle.apply(orderCreatedEvent);
  }
}
