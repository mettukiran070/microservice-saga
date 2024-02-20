package com.example.orderservice.command.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
  private String productId;
  private String userId;
  private String addressId;
  private Integer quantity;
}
