package com.societysphere.controller;

import org.springframework.web.bind.annotation.*;

import com.societysphere.dto.LoginRequest;
import com.societysphere.dto.RegisterRequest;
import com.societysphere.model.User;
import com.societysphere.security.JwtUtil;
import com.societysphere.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService,
                          JwtUtil jwtUtil){
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterRequest request){

        return authService.register(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){

        User user = authService.login(request);

        return jwtUtil.generateToken(user.getEmail());
    }
}