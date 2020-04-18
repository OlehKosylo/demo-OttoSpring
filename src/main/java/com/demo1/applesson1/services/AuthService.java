package com.demo1.applesson1.services;

import com.demo1.applesson1.dto.JwtAuthenticationResponse;
import com.demo1.applesson1.dto.LoginRequest;
import com.demo1.applesson1.dto.UserRequest;
import com.demo1.applesson1.dto.UserResponse;

public interface AuthService {

    UserResponse registerUser(UserRequest userRequest);

    JwtAuthenticationResponse loginUser(LoginRequest loginRequest);

}
