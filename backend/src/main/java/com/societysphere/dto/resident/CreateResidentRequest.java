package com.societysphere.dto.resident;

import com.societysphere.enums.Gender;
import com.societysphere.enums.ResidentType;
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
public class CreateResidentRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotNull(message = "Flat ID is required")
    private Long flatId;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Resident type is required")
    private ResidentType residentType;

    private LocalDate dateOfBirth;

    private String emergencyContactName;

    private String emergencyContactNumber;

    private String occupation;
}
