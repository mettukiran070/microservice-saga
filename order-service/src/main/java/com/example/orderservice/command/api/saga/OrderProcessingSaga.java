package com.example.orderservice.command.api.saga;

import com.example.commonservice.commands.CompleteOrderCommand;
import com.example.commonservice.commands.ShipOrderCommand;
import com.example.commonservice.commands.ValidatePaymentCommand;
import com.example.commonservice.events.OrderCompletedEvent;
import com.example.commonservice.events.OrderShippedEvent;
import com.example.commonservice.events.PaymentProcessedEvent;
import com.example.commonservice.model.User;
import com.example.commonservice.queries.GetUserPaymentDetailsQuery;
import com.example.orderservice.command.api.events.OrderCreatedEvent;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
@Slf4j
public class OrderProcessingSaga {

  @Autowired
  private transient CommandGateway commandGateway;
  @Autowired
  private transient QueryGateway queryGateway;

  public OrderProcessingSaga() {
  }

  @StartSaga
  @SagaEventHandler(associationProperty = "orderId")
  private void handle(OrderCreatedEvent orderCreatedEvent) {
    log.info("Order created event in Saga for order Id : {}", orderCreatedEvent.getOrderId());
    User user = null;
    GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = new GetUserPaymentDetailsQuery(orderCreatedEvent.getUserId());
    try {
      user = this.queryGateway.query(getUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
    } catch (Exception ex) {
      log.error("Unable to fetch the user {}", ex.getMessage());
      // start the compensation transaction
    }
    ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
        .cardDetails(user.getCardDetails())
        .orderId(orderCreatedEvent.getOrderId())
        .paymentId(UUID.randomUUID().toString())
        .build();

    this.commandGateway.sendAndWait(validatePaymentCommand);
  }

  @SagaEventHandler(associationProperty = "orderId")
  private void handle(PaymentProcessedEvent event) {
    log.info("PaymentProcessed Event in SAGA for Order Id {}", event.getOrderId());
    try {
      ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
          .orderId(event.getOrderId())
          .shipmentId(UUID.randomUUID().toString())
          .build();
      commandGateway.send(shipOrderCommand);
    } catch (Exception e) {
     log.error("Payment Processed Event Failed {}", e.getMessage());
     // Start the Compensating Transaction
    }
  }

  @SagaEventHandler(associationProperty = "orderId")
  public void handle(OrderShippedEvent event) {
    log.info("Order Shipped Event in SAGA for Order Id {}", event.getOrderId());
    CompleteOrderCommand command = CompleteOrderCommand.builder()
        .orderId(event.getOrderId())
        .orderStatus("APPROVED")
        .build();
    commandGateway.send(command);
  }

  @SagaEventHandler(associationProperty = "orderId")
  @EndSaga
  public void handle(OrderCompletedEvent event) {
    log.info("Order Completed Event in SAGA for Order Id {}", event.getOrderId());
  }

}
