package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.entity.AppUser;
import com.enigma.sopimart.entity.UserCredential;
import com.enigma.sopimart.repository.UserCredentialRepository;
import com.enigma.sopimart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserCredentialRepository userCredentialRepository;
    @Override
    public AppUser loadUserByUserId(String id) { // method untuk memverifikasi JWT nya
        UserCredential userCredential = userCredentialRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Invalid credential"));
        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getName())
                .build();
    }

    public UserDetails loadUserByUsername (String username) throws  UsernameNotFoundException {  // Method ini untuk cek by usernamenya sebagai authentication untuk login
        UserCredential userCredential = userCredentialRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid credential"));
        return  AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .role(userCredential.getRole().getName())
                .build();
    }
}
