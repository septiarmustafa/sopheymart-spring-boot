package com.enigma.sopimart.controller;

import com.enigma.sopimart.constant.AppPath;
import com.enigma.sopimart.dto.request.AuthRequest;
import com.enigma.sopimart.dto.response.CommonAuthResponse;
import com.enigma.sopimart.dto.response.LoginResponse;
import com.enigma.sopimart.dto.response.RegisterResponse;
import com.enigma.sopimart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.API+AppPath.AUTH)
@CrossOrigin(origins = "http://localhost:5173/")
public class AuthController {
    private final AuthService authService;

    @PostMapping(AppPath.ADMIN + AppPath.REGISTER)
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest authRequest) {
        RegisterResponse registerResponse = authService.registerAdmin(authRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonAuthResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Successfully register")
                        .data(registerResponse).build());
    }
    @PostMapping(AppPath.CUSTOMER + AppPath.REGISTER)
    public ResponseEntity<?> registerNewCustomer(@RequestBody AuthRequest authRequest) {
        RegisterResponse registerResponse = authService.registerCustomer(authRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonAuthResponse.builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Successfully register")
                        .data(registerResponse).build());
    }

    @PostMapping(AppPath.LOGIN)
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        LoginResponse loginResponse = authService.login(authRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonAuthResponse.builder()
                        .status(HttpStatus.OK.value())
                        .message("Successfully login")
                        .data(loginResponse).build());
    }
}
