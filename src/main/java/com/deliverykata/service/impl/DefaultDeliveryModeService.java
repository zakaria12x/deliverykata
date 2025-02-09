package com.deliverykata.service.impl;

import com.deliverykata.dto.CustomerDto;
import com.deliverykata.dto.DeliveryModeDto;
import com.deliverykata.repository.DeliveryModeRepository;
import com.deliverykata.service.DeliveryModeService;
import com.deliverykata.util.DeliveryModeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultDeliveryModeService implements DeliveryModeService {
    private final DeliveryModeRepository deliveryModeRepository;

    @Override
    public Flux<DeliveryModeDto> getAvailableDeliveyModes() {
        return deliveryModeRepository.findByActiveTrue().map(DeliveryModeMapper::convertToDto);
    }

    @Override
    public Mono<DeliveryModeDto> getDeliveryModeById(String deliveryModeById) {
        return deliveryModeRepository.findById(deliveryModeById).map(DeliveryModeMapper::convertToDto);
    }

    @Override
    public Mono<DeliveryModeDto> createDeliveryMode(DeliveryModeDto deliveryMode) {
        return deliveryModeRepository.save(DeliveryModeMapper.convertToModel(deliveryMode)).map(DeliveryModeMapper::convertToDto);
    }
}
