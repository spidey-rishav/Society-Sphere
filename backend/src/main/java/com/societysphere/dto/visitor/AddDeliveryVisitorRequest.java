package com.societysphere.dto.visitor;

import com.societysphere.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddDeliveryVisitorRequest {

    @NotBlank(message = "Visitor name is required")
    private String visitorName;

    private String companyName;

    private VehicleType vehicleType;

    private String vehicleNumber;

    @NotNull(message = "Flat ID is required")
    private Long flatId;

    private String mobileNumber;
}
