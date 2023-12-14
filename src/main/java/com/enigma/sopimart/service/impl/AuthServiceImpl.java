package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.constant.ERole;
import com.enigma.sopimart.dto.request.AuthRequest;
import com.enigma.sopimart.dto.response.LoginResponse;
import com.enigma.sopimart.dto.response.RegisterResponse;
import com.enigma.sopimart.entity.AppUser;
import com.enigma.sopimart.entity.Customer;
import com.enigma.sopimart.entity.Role;
import com.enigma.sopimart.entity.UserCredential;
import com.enigma.sopimart.repository.UserCredentialRepository;
import com.enigma.sopimart.security.JwtUtil;
import com.enigma.sopimart.service.AuthService;
import com.enigma.sopimart.service.CustomerService;
import com.enigma.sopimart.service.RoleService;
import com.enigma.sopimart.util.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;


    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest authRequest) {
        try {
            validationUtil.validate(authRequest);
            // TODO 2 : Set role
            Role role = Role.builder()
                    .name(ERole.ROLE_CUSTOMER)
                    .build();
            role = roleService.getOrSave(role);

            // TODO 1 : Set credential
            UserCredential userCredential = UserCredential.builder()
                    .username(authRequest.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(authRequest.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            // TODO 3 : Set customer
            Customer customer = Customer.builder()
                    .userCredential(userCredential)
                    .name(authRequest.getName())
                    .address(authRequest.getAddress())
                    .mobilePhone(authRequest.getMobilePhone())
                    .email(authRequest.getEmail())
                    .build();
            customerService.createNewCustomer(customer);

            return RegisterResponse.builder()
                    .username(userCredential.getUsername())
                    .role(userCredential.getRole().getName().toString())
                    .build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        // tempat untuk logic login || apakah request kosong atau tidak
        validationUtil.validate(authRequest);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername().toLowerCase().toLowerCase(),
                authRequest.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Object appuser
        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtUtil.generateToken(appUser);
        return LoginResponse.builder()
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }
}
