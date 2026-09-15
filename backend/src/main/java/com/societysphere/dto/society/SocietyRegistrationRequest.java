package com.societysphere.dto.society;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SocietyRegistrationRequest {

    @NotBlank(message = "Society name is required")
    private String societyName;

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    @NotBlank(message = "Address is required")
    private String address;
    private String city;
    private String state;
    private String pincode;

    @NotBlank(message = "Admin name is required")
    private String adminName;

    @NotBlank(message = "Admin email is required")
    @Email(message = "Please enter a valid email address")
    private String adminEmail;

    @NotBlank(message = "Admin mobile number is required")
    private String adminMobile;

}