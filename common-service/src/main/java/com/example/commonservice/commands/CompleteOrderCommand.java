package com.example.commonservice.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompleteOrderCommand {
  @TargetAggregateIdentifier
  private String orderId;
  private String orderStatus;
}
