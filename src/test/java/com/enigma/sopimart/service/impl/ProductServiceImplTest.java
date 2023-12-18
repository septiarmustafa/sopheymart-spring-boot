package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.dto.request.ProductRequest;
import com.enigma.sopimart.dto.request.StoreRequest;
import com.enigma.sopimart.dto.response.ProductResponse;
import com.enigma.sopimart.dto.response.StoreResponse;
import com.enigma.sopimart.entity.Product;
import com.enigma.sopimart.entity.ProductPrice;
import com.enigma.sopimart.repository.ProductRepository;
import com.enigma.sopimart.service.ProductPriceService;
import com.enigma.sopimart.service.ProductService;
import com.enigma.sopimart.service.StoreService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);
    private final ProductPriceService productPriceService = mock(ProductPriceService.class);
    private final StoreService storeService = mock(StoreService.class);
    private final ProductService productService = new ProductServiceImpl(productRepository, storeService, productPriceService);

    public void setUp(){
        // reset mock behavior
        reset(productRepository,storeService,productPriceService);
    }


    @Test
    void createProductAndProductPrice() {
        // store request
        StoreResponse dummyStore = new StoreResponse();
        dummyStore.setId("11");
        dummyStore.setName("wms");
        dummyStore.setNoSiup("12345");

        // behavior storeResponse
        when(storeService.getByIds(anyString())).thenReturn(dummyStore);

        Product saveProduct = new Product();
        saveProduct.setId("1");
        saveProduct.setName("Oreo");
        saveProduct.setDescription("Black");

        // data dummy request // kalau di method asli nya ini bagian productPriceService.create(productPrice)
        ProductRequest dummyRequest = mock(ProductRequest.class);
        when(dummyRequest.getStoreId()).thenReturn(StoreRequest.builder()
                        .id("11")
                .build());
        when(dummyRequest.getProductName()).thenReturn(saveProduct.getName());
        when(dummyRequest.getDesc()).thenReturn(saveProduct.getDescription());
        when(dummyRequest.getPrice()).thenReturn(1000L);
        when(dummyRequest.getStock()).thenReturn(10);

        // call method class
        ProductResponse productResponse = productService.createProductAndProductPrice(dummyRequest);

        // validate response
        assertNotNull(productResponse);
        assertEquals(saveProduct.getName() , productResponse.getProductName());

        // validate that the product price was set correct
        assertEquals(dummyRequest.getPrice(), productResponse.getPrice());
        assertEquals(dummyRequest.getStock() , productResponse.getStock());

        // validate interaction with store
        assertEquals(dummyStore.getId(), productResponse.getStore().getId());

        // verify interaction with mock object // untuk menandakan bahwa sudah di test
        verify(storeService).getByIds(anyString());
        verify(productRepository).saveAndFlush(any(Product.class));
        verify(productPriceService).create(any(ProductPrice.class));
    }

//    @Test
//    void itWillGetProductByParam (){
//        Specification<Product> specification = (root, query,criteriaBuilder) -> {
//
//
//            return query.getRestriction();
//        };
//         Page<ProductResponse> page =productService.getAllByNameOrPrice("nama", 5000L, 1,5);
//
//    }
}