package com.deliverykata.controller;

import com.deliverykata.dto.DeliveryDto;
import com.deliverykata.service.DeliveryModeService;
import com.deliverykata.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;
    private final DeliveryModeService deliveryModeService;

    private static final String ALL_DELIVERIES_REL = "all-deliveries";

    @GetMapping("/all/customer/{customerId}")
    public Flux<EntityModel<DeliveryDto>> getAllCustomerDeliveries(@PathVariable String customerId) {
        return deliveryService.getAllCustomerDeliveries(customerId)
                              .map(delivery -> EntityModel.of(delivery, WebMvcLinkBuilder.linkTo(
                                      WebMvcLinkBuilder.methodOn(this.getClass()).getAllCustomerDeliveries(customerId)).withRel(ALL_DELIVERIES_REL)));
    }

    @GetMapping("/{id}/customer/{customerId}")
    public Mono<EntityModel<DeliveryDto>> getDelivery(@PathVariable String id, @PathVariable String customerId) {
        return deliveryService.getCustomerDeliveryById(id, customerId)
                              .map(delivery -> EntityModel.of(delivery, WebMvcLinkBuilder.linkTo(
                                                                      WebMvcLinkBuilder.methodOn(this.getClass()).getDelivery(id, customerId)).withSelfRel(),
                                                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass())
                                                                                                        .getAllCustomerDeliveries(
                                                                                                                delivery.getCustomerId()))
                                                                               .withRel(ALL_DELIVERIES_REL)));
    }

    @PostMapping
    public Mono<EntityModel<DeliveryDto>> createDelivery(@RequestBody DeliveryDto delivery) {
        return deliveryModeService.getDeliveryModeById(delivery.getDeliveryModeId())
                                  .flatMap(deliveryMode -> deliveryService.createDelivery(delivery)
                                                                          .map(savedDelivery -> EntityModel.of(savedDelivery,
                                                                                                               WebMvcLinkBuilder.linkTo(
                                                                                                                                        WebMvcLinkBuilder.methodOn(
                                                                                                                                                                 this.getClass())
                                                                                                                                                         .getDelivery(
                                                                                                                                                                 savedDelivery.getId(),
                                                                                                                                                                 delivery.getCustomerId()))
                                                                                                                                .withSelfRel(),
                                                                                                               WebMvcLinkBuilder.linkTo(
                                                                                                                                        WebMvcLinkBuilder.methodOn(
                                                                                                                                                                 this.getClass())
                                                                                                                                                         .getAllCustomerDeliveries(
                                                                                                                                                                 delivery.getCustomerId()))
                                                                                                                                .withRel(
                                                                                                                                        ALL_DELIVERIES_REL))))
                                  .switchIfEmpty(Mono.error(new IllegalArgumentException("delivery mode not supported")));
    }
}
