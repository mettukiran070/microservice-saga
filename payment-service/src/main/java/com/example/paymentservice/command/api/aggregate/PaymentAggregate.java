package com.example.paymentservice.command.api.aggregate;

import com.example.commonservice.commands.ValidatePaymentCommand;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class PaymentAggregate {

  @AggregateIdentifier
  private String paymentId;
  private String orderId;
  private String paymentStatus;

  public PaymentAggregate() {}

  public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {
    log.info("Executing the validate payment command for order id {},"
        + " payment id {}", validatePaymentCommand.getOrderId(),
        validatePaymentCommand.getPaymentId());

  }

}
