package com.societysphere.dto.visitor;

import java.time.LocalDateTime;
import java.util.UUID;

import com.societysphere.enums.VehicleType;
import com.societysphere.enums.VisitorStatus;
import com.societysphere.enums.VisitorType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitorResponse {

    private Long id;

    private UUID publicId;

    private Long societyId;

    private String societyName;

    private Long flatId;

    private String flatNumber;

    private Long securityGuardId;

    private String securityGuardName;

    private Long guestPreApprovalId;

    private VisitorType visitorType;

    private String visitorName;

    private String mobileNumber;

    private String companyName;

    private VehicleType vehicleType;

    private String vehicleNumber;

    private VisitorStatus visitorStatus;

    private LocalDateTime entryTime;

    private LocalDateTime exitTime;

}