package com.deliverykata.service;

import com.deliverykata.dto.CustomerDto;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<CustomerDto> getCustomerById(String customerId);

    Mono<CustomerDto> createCustomer(CustomerDto customerDto);
}
