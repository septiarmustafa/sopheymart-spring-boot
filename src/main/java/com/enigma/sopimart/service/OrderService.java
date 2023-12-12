package com.enigma.sopimart.service;

import com.enigma.sopimart.dto.request.OrderRequest;
import com.enigma.sopimart.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createNewOrder(OrderRequest orderRequest);
    OrderResponse getOrderById (String id);
    List<OrderResponse> getAllOrder();


}
