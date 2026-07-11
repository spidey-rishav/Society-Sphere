package com.societysphere.dto.securityguard;

import java.time.LocalDate;

import com.societysphere.enums.Shift;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityGuardResponse {

    private Long id;

    private Long userId;

    private String fullName;

    private String email;

    private String mobileNumber;

    private Long societyId;

    private String societyName;

    private String employeeId;

    private Shift shift;

    private LocalDate joiningDate;

}