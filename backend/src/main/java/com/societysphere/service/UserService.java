package com.societysphere.service;

import com.societysphere.dto.user.UserRequest;
import com.societysphere.dto.user.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponse getUserByPublicId(UUID publicId);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(
            UUID publicId,
            UserRequest request
    );
}