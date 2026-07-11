package com.societysphere.dto.visitor;

import com.societysphere.enums.VehicleType;
import com.societysphere.enums.VisitorType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VisitorRequest {

    @NotNull(message = "Society is required")
    private Long societyId;

    @NotNull(message = "Flat is required")
    private Long flatId;

    @NotNull(message = "Security guard is required")
    private Long securityGuardId;

    private Long guestPreApprovalId;

    @NotNull(message = "Visitor type is required")
    private VisitorType visitorType;

    @NotBlank(message = "Visitor name is required")
    private String visitorName;

    private String mobileNumber;

    private String companyName;

    private VehicleType vehicleType;

    private String vehicleNumber;

}