package com.dong.authservice.service;

import com.dong.authservice.dto.JwtResponse;
import com.dong.authservice.dto.LoginRequest;
import com.dong.authservice.dto.MessageResponse;
import com.dong.authservice.dto.RegisterRequest;

public interface AuthService {
    JwtResponse authenticateUser(LoginRequest loginRequest);
    
    MessageResponse registerUser(RegisterRequest registerRequest);
} 