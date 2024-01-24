package com.enigma.sopimart.controller;

import com.enigma.sopimart.constant.AppPath;
import com.enigma.sopimart.dto.request.ProductRequest;
import com.enigma.sopimart.dto.response.CommonResponse;
import com.enigma.sopimart.dto.response.PagingResponse;
import com.enigma.sopimart.dto.response.ProductResponse;
import com.enigma.sopimart.entity.Product;
import com.enigma.sopimart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.PRODUCT)
@CrossOrigin(origins = "http://localhost:5173/")
public class ProductController {



    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.createProductAndProductPrice(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<ProductResponse>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Successfully create new product")
                        .data(productResponse).build());
    }

    @GetMapping
    public ResponseEntity<?> getAllProduct (){
        List<Product> productResponse = productService.getAllProduct();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Successfully get all product")
                        .data(productResponse).build());
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getByIdProduct (@PathVariable String id){
        ProductResponse productResponse = productService.getByIdProduct(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Successfully get product")
                        .data(productResponse).build());
    }

    @GetMapping(value = AppPath.PAGE)
    public  ResponseEntity<?> getAllProductPage(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "maxPrice", required = false) Long maxPrice,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size
            ){
        Page<ProductResponse> productResponses = productService.getAllByNameOrPrice(name, maxPrice, page,size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(productResponses.getTotalPages())
                .size(size)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Successfully get all product")
                        .data(productResponses.getContent())
                        .paging(pagingResponse)
                        .build());
    }
    @DeleteMapping(value = AppPath.ID)
    public void delete(@PathVariable String id){
        productService.deleteProduct(id);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct (@RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.updateProductAndProductPrice(productRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Successfully update product")
                        .data(productResponse).build());
    }
}
