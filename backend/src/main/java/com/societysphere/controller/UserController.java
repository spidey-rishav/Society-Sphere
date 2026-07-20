package com.societysphere.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.societysphere.dto.user.UserResponse;
import com.societysphere.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getUsers(){
        return userService.getAllUsers();
    }
}