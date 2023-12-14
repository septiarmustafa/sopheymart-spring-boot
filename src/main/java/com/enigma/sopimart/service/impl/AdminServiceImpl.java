package com.enigma.sopimart.service.impl;

import com.enigma.sopimart.dto.request.AdminRequest;
import com.enigma.sopimart.dto.response.AdminResponse;
import com.enigma.sopimart.entity.Admin;
import com.enigma.sopimart.repository.AdminRepository;
import com.enigma.sopimart.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    @Override
    public AdminResponse create(Admin admin) {
        Admin adminSave = adminRepository.saveAndFlush(admin);
        return AdminResponse.builder()
                .id(adminSave.getId())
                .name(adminSave.getName())
                .phoneNumber(adminSave.getPhoneNumber())
                .build();
    }

    @Override
    public AdminResponse getById(String id) {
        return null;
    }

    @Override
    public List<AdminResponse> getAllAdmin() {
        return null;
    }

    @Override
    public AdminResponse updateAdmin(AdminRequest adminRequest) {
        return null;
    }

    @Override
    public void deleteAdmin(String id) {

    }
}
