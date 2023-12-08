package com.enigma.sopimart.controller;

import com.enigma.sopimart.constant.AppPath;
import com.enigma.sopimart.dto.request.CustomerRequest;
import com.enigma.sopimart.dto.response.CustomerResponse;
import com.enigma.sopimart.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.CUSTOMER)
public class CustomerController {

    private final CustomerService customerService;
//
    @PostMapping
    public CustomerResponse createCustomer (@RequestBody CustomerRequest CustomerRequest){
        return customerService.create(CustomerRequest);
    }

    @PutMapping
    public CustomerResponse updateStore(@RequestBody CustomerRequest CustomerRequest){
        return customerService.updateCustomer(CustomerRequest);
    }

    @GetMapping(value = "/{id}")
    public CustomerResponse getById(@PathVariable Integer id){
        return customerService.getById(id);
    }

    @GetMapping
    public List<CustomerResponse> getAll(){
        return customerService.getAllCustomer();
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Integer id){
        customerService.deleteCustomer(id);
    }

}
