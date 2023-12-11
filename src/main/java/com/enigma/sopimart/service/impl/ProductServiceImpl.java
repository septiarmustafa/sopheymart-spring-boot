package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.dto.request.ProductRequest;
import com.enigma.sopimart.dto.response.ProductResponse;
import com.enigma.sopimart.dto.response.StoreResponse;
import com.enigma.sopimart.entity.Product;
import com.enigma.sopimart.entity.ProductPrice;
import com.enigma.sopimart.entity.Store;
import com.enigma.sopimart.repository.ProductRepository;
import com.enigma.sopimart.service.ProductPriceService;
import com.enigma.sopimart.service.ProductService;
import com.enigma.sopimart.service.StoreService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final ProductPriceService productPriceService;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getProductName())
                .description(productRequest.getDesc())
                .build();

        productRepository.save(product);

        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .desc(product.getDescription())
                .build();
    }

    @Override
    public ProductResponse getByIdProduct(String id) {
        Product product = productRepository.findById(id).orElse(null);
        return product == null ? null : ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .desc(product.getDescription())
                .build();
    }

    @Override
    public List<Product> getAllProduct() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(product -> Product.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .productPrice(product.getProductPrice())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(ProductRequest productRequest) {
        Product product = productRepository.findById(productRequest.getProductId()).orElse(null);
        if (product == null) return null;
        product = Product.builder()
                .id(productRequest.getProductId())
                .name(productRequest.getProductName())
                .description(productRequest.getDesc())
                .build();
        productRepository.save(product);
        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .desc(product.getDescription())
                .build();
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public ProductResponse createProductAndProductPrice(ProductRequest productRequest) {
        StoreResponse storeResponse = storeService.getByIds(productRequest.getStoreId().getId());

        Product product = Product.builder()
                .name(productRequest.getProductName())
                .description(productRequest.getDesc())
                .build();
        productRepository.saveAndFlush(product);

        ProductPrice productPrice = ProductPrice.builder()
                .price(productRequest.getPrice())
                .stock(productRequest.getStock())
                .isActive(true)
                .product(product)
                .store(Store.builder()
                        .id(storeResponse.getId())
                        .build())
                .build();

        productPriceService.create(productPrice);
        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .desc(product.getDescription())
                .price(productPrice.getPrice())
                .stock(productPrice.getStock())
                .store(StoreResponse.builder()
                        .id(storeResponse.getId())
                        .noSiup(storeResponse.getNoSiup())
                        .name(storeResponse.getName())
                        .address(storeResponse.getAddress())
                        .mobilePhone(storeResponse.getMobilePhone())
                        .build())
                .build();
    }
}
