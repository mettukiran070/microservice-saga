package com.example.commonservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDetails {

  private String name;
  private String cardNumber;
  private Integer expMonth;
  private Integer expYear;
  private Integer cvv;

}
