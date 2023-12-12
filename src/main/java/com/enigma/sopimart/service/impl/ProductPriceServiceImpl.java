package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.entity.ProductPrice;
import com.enigma.sopimart.repository.ProductPriceRepository;
import com.enigma.sopimart.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

    private final ProductPriceRepository productPriceRepository;
    @Override
    public ProductPrice create(ProductPrice productPrice) {
        return productPriceRepository.save(productPrice);
    }

    @Override
    public ProductPrice getById(String id) {
        return productPriceRepository.findById(id).orElseThrow();
    }

    @Override
    public ProductPrice findProductPriceIsActive(String productId, Boolean isActive) {
        return productPriceRepository.findByProduct_IdAndIsActive(productId, isActive).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }
}
