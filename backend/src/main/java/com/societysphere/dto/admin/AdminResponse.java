package com.societysphere.dto.admin;

import com.societysphere.enums.AdminType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminResponse {

    private Long id;

    private Long userId;

    private String fullName;

    private String email;

    private String mobileNumber;

    private Long societyId;

    private String societyName;

    private AdminType adminType;

    private String designation;

}