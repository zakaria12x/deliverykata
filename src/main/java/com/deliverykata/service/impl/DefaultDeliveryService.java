package com.deliverykata.service.impl;

import com.deliverykata.dto.DeliveryDto;
import com.deliverykata.repository.CustomerRepository;
import com.deliverykata.repository.DeliveryRepository;
import com.deliverykata.service.DeliveryService;
import com.deliverykata.util.DeliveryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class DefaultDeliveryService implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Mono<DeliveryDto> createDelivery(DeliveryDto deliveryDto) {
        return deliveryRepository.save(DeliveryMapper.convertToModel(deliveryDto))
                                 .flatMap(savedDelivery -> customerRepository.findById(deliveryDto.getCustomerId()).flatMap(customer -> {
                                     if (customer.getDeliveryIds() == null) {
                                         customer.setDeliveryIds(new ArrayList<>());
                                     }
                                     customer.getDeliveryIds().add(savedDelivery.getId());
                                     return customerRepository.save(customer);
                                 }).thenReturn(savedDelivery))
                                 .map(DeliveryMapper::convertToDto);
    }

    @Override
    public Mono<DeliveryDto> getCustomerDeliveryById(String deliveryId, String customerId) {
        return deliveryRepository.findByIdAndCustomerId(deliveryId, customerId).map(DeliveryMapper::convertToDto);
    }

    @Override
    public Flux<DeliveryDto> getAllCustomerDeliveries(String customerId) {
        return deliveryRepository.findByCustomerId(customerId).map(DeliveryMapper::convertToDto);
    }

}
