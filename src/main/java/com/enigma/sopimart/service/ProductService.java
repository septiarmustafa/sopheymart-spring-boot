package com.enigma.sopimart.service;

import com.enigma.sopimart.dto.request.ProductRequest;
import com.enigma.sopimart.dto.response.ProductResponse;
import com.enigma.sopimart.entity.Product;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct (ProductRequest productRequest);

    ProductResponse getByIdProduct (String id);

    List<Product> getAllProduct ();

    ProductResponse updateProduct (ProductRequest productRequest);
    void deleteProduct (String id);

    ProductResponse createProductAndProductPrice (ProductRequest productRequest);
}
