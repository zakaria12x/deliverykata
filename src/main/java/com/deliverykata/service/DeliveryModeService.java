package com.deliverykata.service;

import com.deliverykata.dto.CustomerDto;
import com.deliverykata.dto.DeliveryModeDto;
import com.deliverykata.model.DeliveryMode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeliveryModeService {
    Flux<DeliveryModeDto> getAvailableDeliveyModes();

    Mono<DeliveryModeDto> getDeliveryModeById(String deliveryModeById);

    Mono<DeliveryModeDto> createDeliveryMode(DeliveryModeDto deliveryMode);
}
