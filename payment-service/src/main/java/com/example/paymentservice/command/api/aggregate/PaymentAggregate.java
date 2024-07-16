package com.example.paymentservice.command.api.aggregate;

import com.example.commonservice.commands.ValidatePaymentCommand;
import com.example.commonservice.events.PaymentProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class PaymentAggregate {

  @AggregateIdentifier
  private String paymentId;
  private String orderId;
  private String paymentStatus;

  public PaymentAggregate() {}

  @CommandHandler
  public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {
    log.info("Executing the validate payment command for order id {},"
        + " payment id {}", validatePaymentCommand.getOrderId(),
        validatePaymentCommand.getPaymentId());

    PaymentProcessedEvent paymentProcessedEvent = PaymentProcessedEvent.builder()
        .paymentId(validatePaymentCommand.getPaymentId())
        .orderId(validatePaymentCommand.getOrderId())
        .build();

    AggregateLifecycle.apply(paymentProcessedEvent);
    log.info("PaymentProcessed Event Applied {}", paymentProcessedEvent);
  }

  @EventSourcingHandler
  public void on(PaymentProcessedEvent event) {
    this.paymentId = event.getPaymentId();
    this.orderId = event.getOrderId();
  }

}
