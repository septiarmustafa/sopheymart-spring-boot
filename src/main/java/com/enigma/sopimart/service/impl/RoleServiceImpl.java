package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.entity.Role;
import com.enigma.sopimart.repository.RoleRepository;
import com.enigma.sopimart.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public Role getOrSave(Role role) {
        Role optionalRole = roleRepository.findByName(role.getName());
        // jika ada di DB di get
        if (optionalRole != null) return optionalRole;
        // jika tidak ada maka create new
        return roleRepository.save(role);
    }
}
