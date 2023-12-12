package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.dto.request.OrderRequest;
import com.enigma.sopimart.dto.response.*;
import com.enigma.sopimart.entity.Customer;
import com.enigma.sopimart.entity.Order;
import com.enigma.sopimart.entity.OrderDetail;
import com.enigma.sopimart.entity.ProductPrice;
import com.enigma.sopimart.repository.OrderRepository;
import com.enigma.sopimart.service.CustomerService;
import com.enigma.sopimart.service.OrderService;
import com.enigma.sopimart.service.ProductPriceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    final OrderRepository orderRepository;
    final CustomerService customerService;
    final ProductPriceService productPriceService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public OrderResponse createNewOrder(OrderRequest orderRequest) {
        // TODO 1 : validate customer
        CustomerResponse customerResponse = customerService.getById(orderRequest.getCustomerId());

        // TODO 2 : Convert orderDetailRequest to OrderDetail
        List<OrderDetail> orderDetails = orderRequest.getOrderDetailList().stream().map(orderDetailRequest -> {

            // TODO 3 : validate product price
            ProductPrice productPrice = productPriceService.getById(orderDetailRequest.getProductPriceId());
            return OrderDetail.builder()
                    .productPrice(productPrice)
                    .quantity(orderDetailRequest.getQuantity())
                    .build();
        }).toList();

        // TODO 4 : Create New Order
        Order order = Order.builder()
                .customer(Customer.builder()
                        .id(customerResponse.getId())
                        .build())
                .transDate(LocalDateTime.now())
                .orderDetail(orderDetails)
                .build();
        orderRepository.saveAndFlush(order);

        List<OrderDetailResponse> orderDetailResponses = order.getOrderDetail().stream().map(orderDetail -> {
            // TODO 5 : Set order from orderDetail after creating new order
            orderDetail.setOrder(order);
            System.out.println(order);

            // TODO 6 : change the stock from the purchase quantity
            ProductPrice currentProductPrice = orderDetail.getProductPrice();
            currentProductPrice.setStock(currentProductPrice.getStock() - orderDetail.getQuantity());
            return OrderDetailResponse.builder()
                    .orderDetailId(orderDetail.getId())
                    .quantity(orderDetail.getQuantity())

                    // TODO 7 : Convert product to productResponse(productPrice)
                    .product(ProductResponse.builder()
                            .productId(currentProductPrice.getProduct().getId())
                            .productName(currentProductPrice.getProduct().getName())
                            .desc(currentProductPrice.getProduct().getDescription())
                            .stock(currentProductPrice.getStock())
                            .price(currentProductPrice.getPrice())

                            // TODO 8 : convert store to storeResponse(productPrice)
                            .store(StoreResponse.builder()
                                    .id(currentProductPrice.getStore().getId())
                                    .name(currentProductPrice.getStore().getName())
                                    .noSiup(currentProductPrice.getStore().getNoSiup())
                                    .address(currentProductPrice.getStore().getAddress())
                                    .mobilePhone(currentProductPrice.getStore().getMobilePhone())
                                    .build())
                            .build())
                    .build();
        }).toList();

        // TODO 9 : Convert customer to customer response
//        CustomerResponse customer = CustomerResponse.builder()
//                .id(order.getCustomer().getId())
//                .name(order.getCustomer().getName())
//                .email(order.getCustomer().getEmail())
//                .mobilePhone(order.getCustomer().getMobilePhone())
//                .address(order.getCustomer().getAddress())
//                .build();

        // TODO 10 : Return OrderResponse
        return OrderResponse.builder()
                .orderID(order.getId())
                .transDate(order.getTransDate())
                .customer(customerResponse)
                .orderDetailList(orderDetailResponses)
                .build();
    }

    @Override
    public OrderResponse getOrderById(String id) {
        return null;
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        return null;
    }
}
