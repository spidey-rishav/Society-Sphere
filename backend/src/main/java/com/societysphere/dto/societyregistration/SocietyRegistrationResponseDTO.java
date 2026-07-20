package com.societysphere.dto.societyregistration;

import com.societysphere.enums.RegistrationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class SocietyRegistrationResponseDTO {
    private Long id;
    private String societyName;
    private String societyAddress;
    private String city;
    private String state;
    private String pincode;
    private String contactPersonName;
    private String contactNumber;
    private String email;
    private RegistrationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
