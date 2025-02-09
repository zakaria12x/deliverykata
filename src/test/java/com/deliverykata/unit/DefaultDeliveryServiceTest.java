package com.deliverykata.unit;

import com.deliverykata.service.impl.DefaultDeliveryService;
import com.deliverykata.dto.DeliveryDto;
import com.deliverykata.repository.CustomerRepository;
import com.deliverykata.repository.DeliveryRepository;
import com.deliverykata.util.DeliveryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DefaultDeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private DefaultDeliveryService deliveryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateDelivery() {
        DeliveryDto deliveryDto = DeliveryDto.builder()
                                             .id("1")
                                             .customerId("customer123")
                                             .deliveryModeId("mode123")
                                             .deliveryTimeSlot(LocalDateTime.now())
                                             .build();

        when(deliveryRepository.save(any())).thenReturn(Mono.just(DeliveryMapper.convertToModel(deliveryDto)));
        when(customerRepository.findById(eq("customer123"))).thenReturn(Mono.just(com.deliverykata.model.Customer.builder()
                                                                                                                 .id("customer123")
                                                                                                                 .name("John Doe")
                                                                                                                 .email("john@example.com")
                                                                                                                 .deliveryIds(new ArrayList<>())
                                                                                                                 .build()));
        when(customerRepository.save(any())).thenReturn(Mono.empty());

        DeliveryDto savedDelivery = deliveryService.createDelivery(deliveryDto).block();

        assertNotNull(savedDelivery);
        assertEquals("customer123", savedDelivery.getCustomerId());
        assertEquals("mode123", savedDelivery.getDeliveryModeId());

        verify(deliveryRepository, times(1)).save(any());
        verify(customerRepository, times(1)).findById("customer123");
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    public void testGetCustomerDeliveryById() {
        DeliveryDto deliveryDto = DeliveryDto.builder()
                                             .id("1")
                                             .customerId("customer123")
                                             .deliveryModeId("mode123")
                                             .deliveryTimeSlot(LocalDateTime.now())
                                             .build();

        when(deliveryRepository.findByIdAndCustomerId("1", "customer123")).thenReturn(Mono.just(DeliveryMapper.convertToModel(deliveryDto)));

        DeliveryDto delivery = deliveryService.getCustomerDeliveryById("1", "customer123").block();

        assertNotNull(delivery);
        assertEquals("customer123", delivery.getCustomerId());

        verify(deliveryRepository, times(1)).findByIdAndCustomerId("1", "customer123");
    }

    @Test
    public void testGetAllCustomerDeliveries() {
        List<DeliveryDto> deliveries = List.of(
                DeliveryDto.builder().id("1").customerId("customer123").deliveryModeId("mode123").deliveryTimeSlot(LocalDateTime.now()).build(),
                DeliveryDto.builder().id("2").customerId("customer123").deliveryModeId("mode124").deliveryTimeSlot(LocalDateTime.now()).build());

        when(deliveryRepository.findByCustomerId("customer123")).thenReturn(Flux.fromIterable(deliveries).map(DeliveryMapper::convertToModel));

        List<DeliveryDto> deliveryList = deliveryService.getAllCustomerDeliveries("customer123").collectList().block();

        assertNotNull(deliveryList);
        assertEquals(2, deliveryList.size());

        verify(deliveryRepository, times(1)).findByCustomerId("customer123");
    }
}
