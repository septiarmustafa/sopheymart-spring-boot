package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.entity.ProductPrice;
import com.enigma.sopimart.repository.ProductPriceRepository;
import com.enigma.sopimart.service.ProductPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPriceServiceImpl implements ProductPriceService {

    private final ProductPriceRepository productPriceRepository;
    @Override
    public ProductPrice create(ProductPrice productPrice) {
        return productPriceRepository.save(productPrice);
    }
}
