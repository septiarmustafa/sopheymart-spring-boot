package com.enigma.sopimart.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CustomerResponse {
    private Integer id;
    private String name;
    private String address;
    private String mobilePhone;
    private String email;
}
