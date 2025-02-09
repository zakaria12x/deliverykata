package com.deliverykata.controller;

import com.deliverykata.dto.CustomerDto;
import com.deliverykata.dto.DeliveryModeDto;
import com.deliverykata.service.DeliveryModeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/delivery-modes")
public class DeliveryModeController {
    private final DeliveryModeService deliveryModeService;

    @GetMapping("/all")
    public Flux<DeliveryModeDto> getAvailableDeliveyModes() {
        return deliveryModeService.getAvailableDeliveyModes();
    }

    @PostMapping
    public Mono<DeliveryModeDto> createDeliveryMode(@RequestBody DeliveryModeDto deliveryMode) {
        return deliveryModeService.createDeliveryMode(deliveryMode);
    }
}
