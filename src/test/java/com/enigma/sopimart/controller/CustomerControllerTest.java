package com.enigma.sopimart.controller;

import com.enigma.sopimart.dto.request.CustomerRequest;
import com.enigma.sopimart.dto.response.CustomerResponse;
import com.enigma.sopimart.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    @BeforeEach
    public void setUp(){

        // inisialisasi controller dan mock

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createCustomer() {
        CustomerRequest customerRequest = new CustomerRequest();
        CustomerResponse customerResponse = new CustomerResponse();

        when(customerService.create(customerRequest)).thenReturn(customerResponse);

        CustomerResponse actualResponse = customerController.createCustomer(customerRequest);

        assertEquals(customerResponse, actualResponse);
    }

    @Test
    void getById() {
        String id = "1";
        CustomerResponse expectedResponse = new CustomerResponse();

        when(customerService.getById(id)).thenReturn(expectedResponse);

        CustomerResponse actualResponse = customerController.getById(id);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getAll() {
        List<CustomerResponse> expectedResponse = new ArrayList<>();

        when(customerService.getAllCustomer()).thenReturn(expectedResponse);

        List<CustomerResponse> actualResponse = customerController.getAll();

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void updateStore() {
        CustomerRequest customerRequest = new CustomerRequest();
        CustomerResponse customerResponse = new CustomerResponse();

        when(customerService.updateCustomer(customerRequest)).thenReturn(customerResponse);

        CustomerResponse actualResponse = customerController.updateCustomer(customerRequest);

        assertEquals(customerResponse, actualResponse);
    }

    @Test
    void delete() {
        String id = "1";
        customerController.delete(id);
        verify(customerService).deleteCustomer(id);
    }
}