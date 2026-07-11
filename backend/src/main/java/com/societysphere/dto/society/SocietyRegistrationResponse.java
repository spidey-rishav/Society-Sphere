package com.societysphere.dto.society;

import com.societysphere.enums.RegistrationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocietyRegistrationResponse {

    private Long registrationId;

    private String societyName;

    private RegistrationStatus registrationStatus;

    private String message;

}