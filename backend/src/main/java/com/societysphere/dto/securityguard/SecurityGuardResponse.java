package com.societysphere.dto.securityguard;

import com.societysphere.enums.Gender;
import com.societysphere.enums.ShiftType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityGuardResponse {
    private Long id;
    private UUID publicId;
    private String fullName;
    private String email;
    private String mobileNumber;
    private Gender gender;
    private ShiftType shiftType;
    private LocalDate joiningDate;
    private String employeeId;
    private String profileImage;
    private Boolean active;
}