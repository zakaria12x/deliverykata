package com.deliverykata.util;

import com.deliverykata.dto.CustomerDto;
import com.deliverykata.model.Customer;

public class CustomerMapper {
    private CustomerMapper() { }

    public static CustomerDto convertToDto(Customer customer) {
        return CustomerDto.builder().id(customer.getId()).name(customer.getName()).email(customer.getEmail()).build();
    }

    public static Customer convertToModel(CustomerDto customerDto) {
        return Customer.builder().name(customerDto.getName()).email(customerDto.getEmail()).build();
    }
}
