package com.example.orderservice.command.api.aggregate;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class OrderAggregate {

  @AggregateIdentifier
  private String orderId;
  private String productId;
  private String userId;
  private String addressId;
  private Integer quantity;
  private String orderStatus;
}
