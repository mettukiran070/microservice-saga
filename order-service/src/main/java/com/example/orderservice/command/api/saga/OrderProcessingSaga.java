package com.example.orderservice.command.api.saga;

import com.example.commonservice.commands.ValidatePaymentCommand;
import com.example.commonservice.model.User;
import com.example.commonservice.queries.GetUserPaymentDetailsQuery;
import com.example.orderservice.command.api.events.OrderCreatedEvent;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;

@Saga
@Slf4j
public class OrderProcessingSaga {

  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  public OrderProcessingSaga(CommandGateway commandGateway, QueryGateway queryGateway) {
    this.commandGateway = commandGateway;
    this.queryGateway = queryGateway;
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

}
