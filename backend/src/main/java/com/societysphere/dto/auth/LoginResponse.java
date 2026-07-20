package com.societysphere.dto.auth;

import com.societysphere.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String tokenType;
    private Long userId;
    private String fullName;
    private String email;
    private UserRole role;
    private Long societyId;
    private Boolean firstLogin;
}