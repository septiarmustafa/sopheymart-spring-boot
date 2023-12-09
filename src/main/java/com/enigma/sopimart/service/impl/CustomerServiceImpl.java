package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.dto.request.CustomerRequest;
import com.enigma.sopimart.dto.response.CustomerResponse;
import com.enigma.sopimart.entity.Customer;
import com.enigma.sopimart.repository.CustomerRepository;
import com.enigma.sopimart.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .address(customerRequest.getAddress())
                .mobilePhone(customerRequest.getMobilePhone())
                .email(customerRequest.getEmail())
                .build();

        customer = customerRepository.save(customer);

        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .mobilePhone(customer.getMobilePhone())
                .email(customerRequest.getEmail())
                .build();
    }

    @Override
    public CustomerResponse getById(Integer id) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer == null) {
            return null;
        }
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .mobilePhone(customer.getMobilePhone())
                .email(customer.getEmail())
                .build();
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        List<Customer> listCustomer = customerRepository.findAll();
        return listCustomer.stream().map(
                customer -> CustomerResponse.builder()
                        .id(customer.getId())
                        .name(customer.getName())
                        .address(customer.getAddress())
                        .mobilePhone(customer.getMobilePhone())
                        .email(customer.getEmail()).build())
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponse updateCustomer(CustomerRequest customerRequest) {
       Customer customer = customerRepository.findById(customerRequest.getId()).orElse(null);
       if (customer != null) {
           customer = Customer.builder()
                .id(customerRequest.getId())
                .name(customerRequest.getName())
                .address(customerRequest.getAddress())
                .mobilePhone(customerRequest.getMobilePhone())
                .email(customerRequest.getEmail())
                .build();
           customerRepository.save(customer);
           return CustomerResponse.builder()
                   .id(customer.getId())
                   .name(customer.getName())
                   .address(customer.getAddress())
                   .mobilePhone(customer.getMobilePhone())
                   .email(customer.getEmail())
                   .build();
       }
       return null;
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}
