package com.example.orderservice.command.api.aggregate;

import com.example.commonservice.commands.CompleteOrderCommand;
import com.example.commonservice.events.OrderCompletedEvent;
import com.example.orderservice.command.api.command.CreateOrderCommand;
import com.example.orderservice.command.api.events.OrderCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
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

  @CommandHandler
  public OrderAggregate(CreateOrderCommand createOrderCommand) {
    OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
    BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
    AggregateLifecycle.apply(orderCreatedEvent);
  }

  @EventSourcingHandler
  public void on(OrderCreatedEvent orderCreatedEvent) {
    this.orderStatus = orderCreatedEvent.getOrderStatus();
    this.orderId = orderCreatedEvent.getOrderId();
    this.userId = orderCreatedEvent.getUserId();
    this.quantity = orderCreatedEvent.getQuantity();
    this.productId = orderCreatedEvent.getProductId();
    this.addressId = orderCreatedEvent.getAddressId();
  }

  @CommandHandler
  public void handle(CompleteOrderCommand command) {
    OrderCompletedEvent event = OrderCompletedEvent.builder()
        .orderId(command.getOrderId())
        .orderStatus(command.getOrderStatus())
        .build();
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(OrderCompletedEvent event) {
    this.orderStatus = event.getOrderStatus();

  }
}
