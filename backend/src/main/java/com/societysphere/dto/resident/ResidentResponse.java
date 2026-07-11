package com.societysphere.dto.resident;

import java.time.LocalDate;

import com.societysphere.enums.Gender;
import com.societysphere.enums.ResidentType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResidentResponse {

    private Long id;

    private Long userId;

    private String fullName;

    private String email;

    private String mobileNumber;

    private Long societyId;

    private String societyName;

    private Long flatId;

    private String flatNumber;

    private ResidentType residentType;

    private Gender gender;

    private LocalDate dateOfBirth;

    private LocalDate moveInDate;

}