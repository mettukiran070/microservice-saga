package com.example.orderservice.command.api.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
  private String orderId;
  private String productId;
  private String userId;
  private String addressId;
  private Integer quantity;
  private String orderStatus;
}
