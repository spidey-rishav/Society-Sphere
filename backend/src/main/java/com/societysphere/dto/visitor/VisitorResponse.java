package com.societysphere.dto.visitor;

import com.societysphere.enums.VehicleType;
import com.societysphere.enums.VisitorStatus;
import com.societysphere.enums.VisitorType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VisitorResponse {
    private Long id;
    private String visitorName;
    private VisitorType visitorType;
    private String companyName;
    private VehicleType vehicleType;
    private String vehicleNumber;
    private VisitorStatus visitorStatus;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private String flatNumber;
    private String blockName;
    private String guardName;
    private LocalDateTime createdAt;
}