package com.societysphere.dto.guestpreapproval;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GuestPreBookRequest {

    @NotBlank(message = "Guest name is required")
    private String guestName;

    @NotBlank(message = "Guest mobile number is required")
    private String guestMobileNumber;

    @NotBlank(message = "Purpose is required")
    private String purpose;

    @NotNull(message = "Expected arrival time is required")
    private LocalDateTime expectedArrivalTime;

    @NotBlank(message = "Society code is required")
    private String societyCode;
    
    // Add flatNumber to identify resident
    @NotBlank(message = "Flat number is required")
    private String flatNumber;
}
