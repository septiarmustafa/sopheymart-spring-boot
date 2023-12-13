package com.enigma.sopimart.repository;

import com.enigma.sopimart.constant.ERole;
import com.enigma.sopimart.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByName(ERole name);
}
