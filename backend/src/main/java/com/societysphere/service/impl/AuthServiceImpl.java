package com.societysphere.service.impl;

import com.societysphere.dto.auth.LoginRequest;
import com.societysphere.dto.auth.LoginResponse;
import com.societysphere.dto.auth.ProfileCompletionRequest;
import com.societysphere.entity.User;
import com.societysphere.enums.AccountStatus;
import com.societysphere.exception.BadRequestException;
import com.societysphere.exception.ResourceNotFoundException;
import com.societysphere.repository.UserRepository;
import com.societysphere.response.ApiResponse;
import com.societysphere.security.JwtService;
import com.societysphere.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public ApiResponse<LoginResponse> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        if (user.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new BadRequestException("Account is not active");
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);

        LoginResponse loginResponse = new LoginResponse(
                jwtToken,
                "Bearer",
                user.getId(),
                "User FullName Placeholder", // We might need to fetch profile for real name, but keeping it simple as per fields
                user.getEmail(),
                user.getRole(),
                null, // societyId placeholder
                user.getFirstLogin()
        );

        return ApiResponse.success("Login successful", loginResponse);
    }

    @Override
    public ApiResponse<String> completeProfile(ProfileCompletionRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setFirstLogin(false);
        user.setPasswordChangedAt(LocalDateTime.now());
        
        // Optionally save other profile details in a Profile entity if it existed.
        
        userRepository.save(user);

        return ApiResponse.success(null, "Profile completed successfully");
    }
}