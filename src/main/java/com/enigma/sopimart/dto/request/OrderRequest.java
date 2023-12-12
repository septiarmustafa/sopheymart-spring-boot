package com.enigma.sopimart.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class OrderRequest {
    private String customerId;
    private List<OrderDetailRequest> orderDetailList;
}
