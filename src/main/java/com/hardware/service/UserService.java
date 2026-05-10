package com.hardware.service;

import com.hardware.dto.request.ChangePasswordRequest;
import com.hardware.dto.request.UpdateUserRequest;
import com.hardware.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse getCurrentUser(String username);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deactivateUser(Long id, String currentUsername);

    void changePassword(Long id, ChangePasswordRequest request, String currentUsername);
}
