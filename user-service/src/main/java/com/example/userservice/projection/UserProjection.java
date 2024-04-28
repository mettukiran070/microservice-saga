package com.example.userservice.projection;

import com.example.commonservice.model.CardDetails;
import com.example.commonservice.model.User;
import com.example.commonservice.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

  @QueryHandler
  public User getUserPaymentDetails(GetUserPaymentDetailsQuery query) {
    // Ideally fetch the details from DB
    return User.builder()
        .userId(query.getUserId())
        .firstName("Kiran Kumar")
        .lastName("Mettu")
        .cardDetails(CardDetails.builder()
            .name("Kiran Kumar")
            .cardNumber("123412341234")
            .expMonth(04)
            .expYear(2030)
            .cvv(123)
            .build())
        .build();
  }

}
