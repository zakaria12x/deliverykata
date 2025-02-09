package com.deliverykata.repository;

import com.deliverykata.model.DeliveryMode;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface DeliveryModeRepository extends ReactiveMongoRepository<DeliveryMode, String> {
    Flux<DeliveryMode> findByActiveTrue();
}