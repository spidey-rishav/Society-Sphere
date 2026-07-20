package com.societysphere.dto.visitor;

import com.societysphere.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GuestVerifyRequest {

    @NotBlank(message = "Barcode is required")
    private String barcode;

    private VehicleType vehicleType;

    private String vehicleNumber;
}
