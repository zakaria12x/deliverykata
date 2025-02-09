package com.deliverykata.service;

import com.deliverykata.dto.DeliveryDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeliveryService {
    Mono<DeliveryDto> createDelivery(DeliveryDto delivery);

    Mono<DeliveryDto> getCustomerDeliveryById(String deliveryId, String customerId);

    Flux<DeliveryDto> getAllCustomerDeliveries(String customerId);
}
