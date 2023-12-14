package com.enigma.sopimart.service;

import com.enigma.sopimart.dto.request.AuthRequest;
import com.enigma.sopimart.dto.response.LoginResponse;
import com.enigma.sopimart.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer (AuthRequest authRequest);
    RegisterResponse registerAdmin (AuthRequest authRequest);
    LoginResponse login(AuthRequest authRequest);
}
