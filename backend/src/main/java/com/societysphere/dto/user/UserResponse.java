package com.societysphere.dto.user;

import com.societysphere.enums.UserRole;
import com.societysphere.enums.AccountStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;

    private String fullName;

    private String email;

    private String mobileNumber;

    private UserRole role;

    private AccountStatus status;

}