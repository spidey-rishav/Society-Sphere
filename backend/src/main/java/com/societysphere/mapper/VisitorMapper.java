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

        VisitorResponse.VisitorResponseBuilder builder = VisitorResponse.builder()
                .id(visitor.getId());

        if (visitor.getFlat() != null) {
            builder.flatNumber(visitor.getFlat().getFlatNumber());
            builder.blockName(visitor.getFlat().getBlock());
        }

        if (visitor.getSecurityGuard() != null) {
            builder.guardName(visitor.getSecurityGuard().getFullName());
        }

        return builder
                .visitorType(visitor.getVisitorType())
                .visitorName(visitor.getVisitorName())
                .companyName(visitor.getCompanyName())
                .vehicleType(visitor.getVehicleType())
                .vehicleNumber(visitor.getVehicleNumber())
                .visitorStatus(visitor.getVisitorStatus())
                .entryTime(visitor.getEntryTime())
                .exitTime(visitor.getExitTime())
                .build();
    }

}