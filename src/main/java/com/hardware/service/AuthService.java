package com.hardware.service;

import com.hardware.dto.request.LoginRequest;
import com.hardware.dto.request.RegisterRequest;
import com.hardware.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse register(RegisterRequest request);
}
