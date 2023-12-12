package com.enigma.sopimart.repository;

import com.enigma.sopimart.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, String> {
    // query method
    Optional<ProductPrice> findByProduct_IdAndIsActive(String productId, Boolean isActive);
}
