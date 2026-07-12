package com.societysphere.mapper;

import com.societysphere.dto.securityguard.SecurityGuardRequest;
import com.societysphere.dto.securityguard.SecurityGuardResponse;
import com.societysphere.entity.SecurityGuard;

public final class SecurityGuardMapper {

    private SecurityGuardMapper() {
    }

    public static SecurityGuard toEntity(SecurityGuardRequest request) {

        if (request == null) {
            return null;
        }

        SecurityGuard securityGuard = new SecurityGuard();

        securityGuard.setEmployeeId(request.getEmployeeId());
        securityGuard.setJoiningDate(request.getJoiningDate());

        return securityGuard;
    }

    public static SecurityGuardResponse toResponse(SecurityGuard securityGuard) {

        if (securityGuard == null) {
            return null;
        }

        SecurityGuardResponse response = new SecurityGuardResponse();

        response.setId(securityGuard.getId());

        if (securityGuard.getUser() != null) {
            response.setUserId(securityGuard.getUser().getId());
            response.setEmail(securityGuard.getUser().getEmail());
        }

        response.setFullName(securityGuard.getFullName());
        response.setMobileNumber(securityGuard.getMobileNumber());

        if (securityGuard.getSociety() != null) {
            response.setSocietyId(securityGuard.getSociety().getId());
            response.setSocietyName(securityGuard.getSociety().getSocietyName());
        }

        response.setEmployeeId(securityGuard.getEmployeeId());
        response.setJoiningDate(securityGuard.getJoiningDate());

        /*
         * Shift mapping is intentionally omitted because:
         *
         * Entity  : ShiftType
         * DTO     : Shift
         *
         * These are different enum types. The conversion should either be
         * handled after unifying the enums or by introducing an explicit
         * enum conversion method in the future.
         */

        return response;
    }

}