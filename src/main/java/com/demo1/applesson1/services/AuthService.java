package com.demo1.applesson1.services;

import com.demo1.applesson1.dto.JwtAuthenticationResponse;
import com.demo1.applesson1.dto.LoginRequest;
import com.demo1.applesson1.dto.UserRequest;

public interface AuthService {

    void registerUser(UserRequest userRequest);

    JwtAuthenticationResponse loginUser(LoginRequest loginRequest);

    void activateAccount(String token);
}
