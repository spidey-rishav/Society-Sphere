package com.societysphere.dto.securityguard;

import java.time.LocalDate;

import com.societysphere.enums.Shift;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityGuardRequest {

    @NotNull(message = "User is required")
    private Long userId;

    @NotNull(message = "Society is required")
    private Long societyId;

    @NotBlank(message = "Employee ID is required")
    private String employeeId;

    @NotNull(message = "Shift is required")
    private Shift shift;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;

}