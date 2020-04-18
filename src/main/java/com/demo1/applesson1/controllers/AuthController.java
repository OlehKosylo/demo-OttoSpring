package com.demo1.applesson1.controllers;

import com.demo1.applesson1.dto.JwtAuthenticationResponse;
import com.demo1.applesson1.dto.LoginRequest;
import com.demo1.applesson1.dto.UserRequest;
import com.demo1.applesson1.dto.UserResponse;
import com.demo1.applesson1.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public HttpEntity<UserResponse> register(@RequestBody UserRequest userRequest) {


        return new ResponseEntity<>(authService.registerUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/login")
    public HttpEntity<JwtAuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {


        return new ResponseEntity<>(authService.loginUser(loginRequest), HttpStatus.OK);
    }
}
