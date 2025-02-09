package com.deliverykata.repository;

import com.deliverykata.model.Delivery;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeliveryRepository extends ReactiveMongoRepository<Delivery, String> {
    Mono<Delivery> findByIdAndCustomerId(String id, String customerId);

    Flux<Delivery> findByCustomerId(String customerId);
}
