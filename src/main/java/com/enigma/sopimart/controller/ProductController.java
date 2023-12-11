package com.enigma.sopimart.controller;

import com.enigma.sopimart.dto.request.ProductRequest;
import com.enigma.sopimart.dto.response.CommonResponse;
import com.enigma.sopimart.dto.response.ProductResponse;
import com.enigma.sopimart.dto.response.StoreResponse;
import com.enigma.sopimart.entity.Product;
import com.enigma.sopimart.service.ProductPriceService;
import com.enigma.sopimart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.createProductAndProductPrice(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<ProductResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully created new product")
                        .data(productResponse).build());
    }

    @GetMapping
    public ResponseEntity<?> getAllProduct (){
        List<Product> productResponse = productService.getAllProduct();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully get all product")
                        .data(productResponse).build());
    }
}