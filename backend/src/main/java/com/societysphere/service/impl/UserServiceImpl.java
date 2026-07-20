package com.societysphere.service.impl;

import com.societysphere.dto.user.UserRequest;
import com.societysphere.dto.user.UserResponse;
import com.societysphere.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserResponse getUserByPublicId(UUID publicId) {
        return null;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return Collections.emptyList();
    }

    @Override
    public UserResponse updateUser(UUID publicId, UserRequest request) {
        return null;
    }
}
