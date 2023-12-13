package com.enigma.sopimart.mapper;

import com.enigma.sopimart.dto.request.CustomerRequest;
import com.enigma.sopimart.dto.response.CustomerResponse;
import com.enigma.sopimart.entity.Customer;

public class CustomerMapperDto {
    public CustomerResponse toDtoResponseCustomer (Customer entity) {
        return CustomerResponse.builder().build();
    }

    public CustomerRequest toDtoRequestCustomer (Customer entity) {
        return CustomerRequest.builder().build();
    }
}



