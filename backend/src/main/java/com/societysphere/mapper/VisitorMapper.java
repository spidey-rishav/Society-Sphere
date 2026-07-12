package com.societysphere.mapper;

import com.societysphere.dto.visitor.VisitorRequest;
import com.societysphere.dto.visitor.VisitorResponse;
import com.societysphere.entity.Visitor;

public final class VisitorMapper {

    private VisitorMapper() {
    }

    public static Visitor toEntity(VisitorRequest request) {

        if (request == null) {
            return null;
        }

        Visitor visitor = new Visitor();

        visitor.setVisitorType(request.getVisitorType());
        visitor.setVisitorName(request.getVisitorName());
        visitor.setMobileNumber(request.getMobileNumber());
        visitor.setCompanyName(request.getCompanyName());
        visitor.setVehicleType(request.getVehicleType());
        visitor.setVehicleNumber(request.getVehicleNumber());

        return visitor;
    }

    public static VisitorResponse toResponse(Visitor visitor) {

        if (visitor == null) {
            return null;
        }

        VisitorResponse response = new VisitorResponse();

        response.setId(visitor.getId());
        response.setPublicId(visitor.getPublicId());

        if (visitor.getSociety() != null) {
            response.setSocietyId(visitor.getSociety().getId());
            response.setSocietyName(visitor.getSociety().getSocietyName());
        }

        if (visitor.getFlat() != null) {
            response.setFlatId(visitor.getFlat().getId());
            response.setFlatNumber(visitor.getFlat().getFlatNumber());
        }

        if (visitor.getSecurityGuard() != null) {
            response.setSecurityGuardId(visitor.getSecurityGuard().getId());
            response.setSecurityGuardName(visitor.getSecurityGuard().getFullName());
        }

        if (visitor.getGuestPreApproval() != null) {
            response.setGuestPreApprovalId(
                    visitor.getGuestPreApproval().getId()
            );
        }

        response.setVisitorType(visitor.getVisitorType());
        response.setVisitorName(visitor.getVisitorName());
        response.setMobileNumber(visitor.getMobileNumber());
        response.setCompanyName(visitor.getCompanyName());
        response.setVehicleType(visitor.getVehicleType());
        response.setVehicleNumber(visitor.getVehicleNumber());
        response.setVisitorStatus(visitor.getVisitorStatus());
        response.setEntryTime(visitor.getEntryTime());
        response.setExitTime(visitor.getExitTime());

        return response;
    }

}