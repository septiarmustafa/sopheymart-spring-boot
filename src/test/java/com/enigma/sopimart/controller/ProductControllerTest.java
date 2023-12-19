package com.enigma.sopimart.controller;

import com.enigma.sopimart.dto.request.ProductRequest;
import com.enigma.sopimart.dto.response.CommonResponse;
import com.enigma.sopimart.dto.response.ProductResponse;
import com.enigma.sopimart.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductService productService;

    @BeforeEach
    public void setUp (){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void createProduct() {
        //dataDummy
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("Oreo");
        productRequest.setPrice(5000L);

        //data response
        ProductResponse productResponse = new ProductResponse();
        productRequest.setProductId("1");
        productRequest.setProductName("Oreo");
        productRequest.setPrice(4000L);

        when(productService.createProductAndProductPrice(productRequest)).thenReturn(productResponse);

        ResponseEntity<?> responseEntity = productController.createProduct(productRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        CommonResponse<ProductResponse> commonResponse = (CommonResponse<ProductResponse>) responseEntity.getBody();
        assertEquals(HttpStatus.CREATED.value(), commonResponse.getStatusCode());
        assertEquals("Successfully create new product", commonResponse.getMessage());
    }

    @Test
    void getAllProduct() {
    }

    @Test
    void getAllProductPage() {
    }
}