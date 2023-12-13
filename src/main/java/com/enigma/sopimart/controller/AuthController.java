package com.enigma.sopimart.controller;

import com.enigma.sopimart.constant.AppPath;
import com.enigma.sopimart.dto.request.AuthRequest;
import com.enigma.sopimart.dto.response.CommonAuthResponse;
import com.enigma.sopimart.dto.response.RegisterResponse;
import com.enigma.sopimart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.API+AppPath.AUTH)
public class AuthController {
    private final AuthService authService;

    @PostMapping(AppPath.REGISTER + AppPath.CUSTOMER)
    public ResponseEntity<?> registerNewCustomer(@RequestBody AuthRequest authRequest) {
        RegisterResponse registerResponse = authService.registerCustomer(authRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonAuthResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully register")
                        .data(registerResponse).build());
    }
}
