package com.deliverykata.util;

import com.deliverykata.dto.DeliveryDto;
import com.deliverykata.model.Delivery;

public class DeliveryMapper {

    private DeliveryMapper() { }

    public static DeliveryDto convertToDto(Delivery delivery) {
        return DeliveryDto.builder()
                          .id(delivery.getId())
                          .deliveryModeId(delivery.getId())
                          .customerId(delivery.getCustomerId())
                          .deliveryModeId(delivery.getDeliveryModeId())
                          .deliveryTimeSlot(delivery.getDeliveryTimeSlot())
                          .build();
    }

    public static Delivery convertToModel(DeliveryDto deliveryDto) {
        return Delivery.builder()
                       .deliveryModeId(deliveryDto.getDeliveryModeId())
                       .customerId(deliveryDto.getCustomerId())
                       .deliveryTimeSlot(deliveryDto.getDeliveryTimeSlot())
                       .build();
    }
}
