package com.enigma.sopimart.service;

import com.enigma.sopimart.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AppUser loadUserByUserId (String id);
}
