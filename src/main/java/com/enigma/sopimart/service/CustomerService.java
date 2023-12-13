package com.enigma.sopimart.service;

import com.enigma.sopimart.dto.request.CustomerRequest;
import com.enigma.sopimart.dto.response.CustomerResponse;
import com.enigma.sopimart.entity.Customer;

import java.util.List;

public interface CustomerService {

    CustomerResponse create (CustomerRequest customerRequest);

    CustomerResponse createNewCustomer (Customer customer);


    CustomerResponse getById (String id);

    List<CustomerResponse> getAllCustomer ();

    CustomerResponse updateCustomer (CustomerRequest customerRequest);
    void deleteCustomer (String id);
}
