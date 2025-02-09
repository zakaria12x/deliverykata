package com.deliverykata.controller;

import com.deliverykata.dto.CustomerDto;
import com.deliverykata.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public Mono<CustomerDto> createCustomer(@RequestBody CustomerDto customer) {
        return customerService.createCustomer(customer);
    }
}
