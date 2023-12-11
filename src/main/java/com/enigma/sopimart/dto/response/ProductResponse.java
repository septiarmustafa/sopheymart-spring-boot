package com.enigma.sopimart.dto.response;


import com.enigma.sopimart.entity.ProductPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProductResponse {
    private String productId;
    private String productName;
    private String desc;
    private Long price;
    private Integer stock;
    private StoreResponse store;
//    private List<ProductPrice> productPrices;

}
