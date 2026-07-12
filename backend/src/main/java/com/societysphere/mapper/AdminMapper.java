package com.societysphere.mapper;

import com.societysphere.dto.admin.AdminRequest;
import com.societysphere.dto.admin.AdminResponse;
import com.societysphere.entity.Admin;

public final class AdminMapper {

    private AdminMapper() {
    }

    public static Admin toEntity(AdminRequest request) {

        if (request == null) {
            return null;
        }

        Admin admin = new Admin();

        admin.setAdminType(request.getAdminType());

        return admin;
    }

    public static AdminResponse toResponse(Admin admin) {

        if (admin == null) {
            return null;
        }

        AdminResponse response = new AdminResponse();

        response.setId(admin.getId());

        if (admin.getUser() != null) {
            response.setUserId(admin.getUser().getId());
            response.setEmail(admin.getUser().getEmail());
        }

        response.setFullName(admin.getFullName());
        response.setMobileNumber(admin.getMobileNumber());

        if (admin.getSociety() != null) {
            response.setSocietyId(admin.getSociety().getId());
            response.setSocietyName(admin.getSociety().getSocietyName());
        }

        response.setAdminType(admin.getAdminType());

        return response;
    }

}