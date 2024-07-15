package com.example.paymentservice.command.api.events;

import com.example.commonservice.events.PaymentProcessedEvent;
import com.example.paymentservice.command.api.entity.Payment;
import com.example.paymentservice.command.api.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentsEventHandler {

  private final PaymentRepository paymentRepository;

  @EventHandler
  public void on(PaymentProcessedEvent event) {
    Payment payment = Payment.builder()
        .paymentId(event.getPaymentId())
        .orderId(event.getOrderId())
        .paymentStatus("COMPLETED")
        .build();

    paymentRepository.save(payment);

  }

}
