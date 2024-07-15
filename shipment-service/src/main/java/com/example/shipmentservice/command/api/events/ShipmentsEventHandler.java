package com.example.shipmentservice.command.api.events;

import com.example.commonservice.events.OrderShippedEvent;
import com.example.shipmentservice.command.api.entity.Shipment;
import com.example.shipmentservice.command.api.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShipmentsEventHandler {

  private final ShipmentRepository shipmentRepository;

  @EventHandler
  public void on(OrderShippedEvent event) {
    Shipment shipment = Shipment.builder().build();
    BeanUtils.copyProperties(event, shipment);
    shipmentRepository.save(shipment);

  }

}
