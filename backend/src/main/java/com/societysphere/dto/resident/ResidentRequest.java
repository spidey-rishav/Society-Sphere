package com.societysphere.dto.resident;

import java.time.LocalDate;

import com.societysphere.enums.Gender;
import com.societysphere.enums.ResidentType;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResidentRequest {

    @NotNull(message = "User is required")
    private Long userId;

    @NotNull(message = "Society is required")
    private Long societyId;

    @NotNull(message = "Flat is required")
    private Long flatId;

    @NotNull(message = "Resident type is required")
    private ResidentType residentType;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "Move-in date is required")
    private LocalDate moveInDate;

}