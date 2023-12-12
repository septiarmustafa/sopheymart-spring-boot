package com.enigma.sopimart.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderResponse {
    private String orderID;
    private LocalDateTime transDate;
    private CustomerResponse customer;
    private List<OrderDetailResponse> orderDetailList;
}
