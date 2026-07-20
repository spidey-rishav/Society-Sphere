package com.societysphere.controller;

import com.societysphere.dto.auth.LoginRequest;
import com.societysphere.dto.auth.LoginResponse;
import com.societysphere.dto.auth.ProfileCompletionRequest;
import com.societysphere.response.ApiResponse;
import com.societysphere.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/complete-profile")
    public ApiResponse<String> completeProfile(
            @Valid @RequestBody ProfileCompletionRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return authService.completeProfile(request, userDetails.getUsername());
    }
}