package com.example.shipmentservice.command.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Shipment {
  @Id
  private String shipmentId;
  private String orderId;
  private String shipmentStatus;

}
