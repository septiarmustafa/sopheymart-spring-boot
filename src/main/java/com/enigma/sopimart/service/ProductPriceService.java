package com.enigma.sopimart.service;

import com.enigma.sopimart.entity.ProductPrice;

public interface ProductPriceService {
   ProductPrice create (ProductPrice product);
   ProductPrice getById (String id);
   ProductPrice findProductPriceIsActive(String productId, Boolean isActive);
}
