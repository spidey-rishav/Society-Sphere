package com.societysphere.service;

import com.societysphere.dto.auth.LoginRequest;
import com.societysphere.dto.auth.LoginResponse;
import com.societysphere.dto.auth.ProfileCompletionRequest;
import com.societysphere.response.ApiResponse;

public interface AuthService {
    ApiResponse<LoginResponse> login(LoginRequest request);
    ApiResponse<String> completeProfile(ProfileCompletionRequest request, String email);
}