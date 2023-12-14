package com.enigma.sopimart.service;

import com.enigma.sopimart.dto.request.AdminRequest;
import com.enigma.sopimart.dto.response.AdminResponse;
import com.enigma.sopimart.entity.Admin;

import java.util.List;

public interface AdminService {
    AdminResponse create (Admin admin);

    AdminResponse getById (String id);

    List<AdminResponse> getAllAdmin();

    AdminResponse updateAdmin (AdminRequest adminRequest);
    void deleteAdmin (String id);
}
