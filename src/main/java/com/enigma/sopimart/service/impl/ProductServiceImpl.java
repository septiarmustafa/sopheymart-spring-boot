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
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    public Page<ProductResponse> getAllByNameOrPrice(String name, Long maxPrice, Integer page, Integer size) {
        // specification untuk menentukan kriteria pencarian, disini criteria pencarian ditandakan dengan root, root yang dimaksud adalah entity product
        Specification<Product> specification = (root, query, criteriaBuilder) -> {
            // join digunakan untuk merelasikan antara product dan product price
            Join<Product, ProductPrice> productPriceJoin = root.join("productPrice");
            // predicate digunakan untuk menggunakanLIKE dimana nanti kita akan menggunakan kondisi pencarian parameter
            // disini kita akan mencari nama produk atau harga yang sama atau harga dibawahnya, makanya menggunakan LessThanOrEquals
            List<Predicate> predicates = new ArrayList<>();
            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%" ));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(productPriceJoin.get("price"), maxPrice));
            }

            // kode return mengembalikan query dimana pada dasarnya kita membangun klause where yang sudah ditentukan dari predicate atau criteria
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(specification,pageable);
        // ini digunakan untuk menyimpan response product yang baru
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products.getContent()) {
            // for disini digunakan untuk mengiterasi daftar product yang disimpan dalam object
            Optional<ProductPrice> productPrice = product.getProductPrice() // optional ini untuk mencari harga yang aktif
                    .stream()
                    .filter(ProductPrice::getIsActive).findFirst();
            if (productPrice.isEmpty()) continue; // kondisi ini digunakan untuk memeriksa apakah productPricenya kosong atau tidak, jika kosong maka di skip
            Store store = productPrice.get().getStore(); // ini digunakan untuk jika harga product yang aktif ditemukan di store
            productResponses.add(
                    ProductResponse.builder()
                            .productId(product.getId())
                            .productName(product.getName())
                            .desc(product.getDescription())
                            .price(productPrice.get().getPrice())
                            .stock(productPrice.get().getStock())
                            .store(StoreResponse.builder()
                                    .id(store.getId())
                                    .name(store.getName())
                                    .noSiup(store.getNoSiup())
                                    .address(store.getAddress())
                                    .mobilePhone(store.getMobilePhone())
                                    .build())
                    .build());
        }
        return new PageImpl<>(productResponses, pageable, products.getTotalElements());
    }
}
