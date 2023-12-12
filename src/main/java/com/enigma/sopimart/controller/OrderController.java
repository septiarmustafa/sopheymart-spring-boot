package com.enigma.sopimart.controller;

import com.enigma.sopimart.constant.AppPath;
import com.enigma.sopimart.dto.request.OrderRequest;
import com.enigma.sopimart.dto.response.CommonResponse;
import com.enigma.sopimart.dto.response.OrderResponse;
import com.enigma.sopimart.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.ORDER)
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.createNewOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully create new order")
                        .data(orderResponse).build());
    }

}
