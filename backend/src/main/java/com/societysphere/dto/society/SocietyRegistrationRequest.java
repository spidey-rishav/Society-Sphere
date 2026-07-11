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

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Pincode is required")
    private String pincode;

    @NotBlank(message = "Admin name is required")
    private String adminName;

    @NotBlank(message = "Admin email is required")
    @Email(message = "Please enter a valid email address")
    private String adminEmail;

    @NotBlank(message = "Admin mobile number is required")
    private String adminMobile;

}