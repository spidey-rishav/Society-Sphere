package com.societysphere.dto.resident;

import com.societysphere.enums.Gender;
import com.societysphere.enums.ResidentType;
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
public class ResidentResponse {
    private Long id;
    private UUID publicId;
    private String fullName;
    private String email;
    private String mobileNumber;
    private Gender gender;
    private ResidentType residentType;
    private String flatNumber;
    private String blockName;
    private String societyName;
    private Boolean active;
    private LocalDate dateOfBirth;
    private String occupation;
    private String profileImage;
}