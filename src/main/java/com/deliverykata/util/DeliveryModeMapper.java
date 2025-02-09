package com.deliverykata.util;

import com.deliverykata.dto.DeliveryModeDto;
import com.deliverykata.model.DeliveryMode;

public class DeliveryModeMapper {
    private DeliveryModeMapper() { }

    public static DeliveryModeDto convertToDto(DeliveryMode deliveryMode) {
        return DeliveryModeDto.builder().id(deliveryMode.getId()).name(deliveryMode.getName()).active(deliveryMode.isActive()).build();
    }

    public static DeliveryMode convertToModel(DeliveryModeDto deliveryModeDto) {
        return DeliveryMode.builder().name(deliveryModeDto.getName()).active(deliveryModeDto.isActive()).build();
    }
}
