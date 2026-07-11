package com.societysphere.dto.guestpreapproval;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestPreApprovalRequest {

    @NotNull(message = "Resident is required")
    private Long residentId;

    @NotBlank(message = "Guest name is required")
    private String guestName;

    @NotBlank(message = "Guest mobile number is required")
    private String guestMobileNumber;

    @NotNull(message = "Visit date is required")
    private LocalDate visitDate;

    @NotBlank(message = "Purpose is required")
    private String purpose;

}