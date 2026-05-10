package com.hardware.service.impl;

import com.hardware.dto.request.ChangePasswordRequest;
import com.hardware.dto.request.UpdateUserRequest;
import com.hardware.dto.response.UserResponse;
import com.hardware.entity.User;
import com.hardware.enums.Role;
import com.hardware.exception.ResourceNotFoundException;
import com.hardware.repository.UserRepository;
import com.hardware.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUserById(Long id) {
        return toResponse(findById(id));
    }

    @Override
    public UserResponse getCurrentUser(String username) {
        return toResponse(findByUsername(username));
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = findById(id);
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
        if (request.getActive() != null) {
            user.setActive(request.getActive());
        }
        return toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deactivateUser(Long id, String currentUsername) {
        User current = findByUsername(currentUsername);
        if (current.getId().equals(id)) {
            throw new IllegalArgumentException("You cannot deactivate your own account");
        }
        User target = findById(id);
        target.setActive(false);
        userRepository.save(target);
    }

    @Override
    @Transactional
    public void changePassword(Long id, ChangePasswordRequest request, String currentUsername) {
        User target = findById(id);
        User current = findByUsername(currentUsername);

        boolean isAdmin = current.getRole() == Role.ADMIN;
        boolean isOwnAccount = current.getId().equals(id);

        if (!isAdmin && !isOwnAccount) {
            throw new AccessDeniedException("You can only change your own password");
        }

        if (!isAdmin) {
            if (request.getCurrentPassword() == null ||
                    !passwordEncoder.matches(request.getCurrentPassword(), target.getPassword())) {
                throw new IllegalArgumentException("Current password is incorrect");
            }
        }

        target.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(target);
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    private UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .active(user.isActive())
                .build();
    }
}
