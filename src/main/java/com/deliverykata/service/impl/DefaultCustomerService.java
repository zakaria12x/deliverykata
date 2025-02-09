package com.deliverykata.service.impl;

import com.deliverykata.dto.CustomerDto;
import com.deliverykata.repository.CustomerRepository;
import com.deliverykata.service.CustomerService;
import com.deliverykata.util.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultCustomerService implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Mono<CustomerDto> getCustomerById(String customerId) {
        return customerRepository.findById(customerId).map(CustomerMapper::convertToDto);
    }

    @Override
    public Mono<CustomerDto> createCustomer(CustomerDto customerDto) {
        return customerRepository.save(CustomerMapper.convertToModel(customerDto)).map(CustomerMapper::convertToDto);
    }
}
