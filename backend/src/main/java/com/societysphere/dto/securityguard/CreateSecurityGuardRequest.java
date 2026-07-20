package com.societysphere.dto.securityguard;

import com.societysphere.enums.Gender;
import com.societysphere.enums.ShiftType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSecurityGuardRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Shift type is required")
    private ShiftType shiftType;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;

    @NotBlank(message = "Employee ID is required")
    private String employeeId;
}
