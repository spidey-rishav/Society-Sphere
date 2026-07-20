package com.societysphere.dto.societyregistration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocietyRegistrationRequestDTO {
    private String societyName;
    private String societyAddress;
    private String city;
    private String state;
    private String pincode;
    private String contactPersonName;
    private String contactNumber;
    private String email;
}
