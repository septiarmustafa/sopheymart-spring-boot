package com.enigma.sopimart.service;

import com.enigma.sopimart.dto.request.CustomerRequest;
import com.enigma.sopimart.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse create (CustomerRequest customerRequest);

    CustomerResponse getById (Integer id);

    List<CustomerResponse> getAllCustomer ();

    CustomerResponse updateCustomer (CustomerRequest customerRequest);
    void deleteCustomer (Integer id);
}
