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

    @PostMapping
    public CustomerResponse createCustomer (@RequestBody CustomerRequest CustomerRequest){
        return customerService.create(CustomerRequest);
    }

    @PutMapping
    public CustomerResponse updateCustomer(@RequestBody CustomerRequest CustomerRequest){
        return customerService.updateCustomer(CustomerRequest);
    }

    @GetMapping(value = AppPath.ID)
    public CustomerResponse getById(@PathVariable String id){
        return customerService.getById(id);
    }

    @GetMapping
    public List<CustomerResponse> getAll(){
        return customerService.getAllCustomer();
    }

    @DeleteMapping(value = AppPath.ID)
    public void delete(@PathVariable String id){
        customerService.deleteCustomer(id);
    }

}
