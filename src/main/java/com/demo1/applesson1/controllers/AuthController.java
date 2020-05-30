package com.demo1.applesson1.controllers;

import com.demo1.applesson1.dto.Request.RecoverUserPasswordRequest;
import com.demo1.applesson1.dto.Response.JwtAuthenticationResponse;
import com.demo1.applesson1.dto.Request.LoginRequest;
import com.demo1.applesson1.dto.Request.UserRequest;
import com.demo1.applesson1.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public HttpStatus register(@Valid @RequestBody UserRequest userRequest) {

        authService.registerUser(userRequest);
        return HttpStatus.OK;
    }

    @PostMapping("/login")
    public HttpEntity<JwtAuthenticationResponse> login(@Valid @RequestBody LoginRequest loginRequest) {

        return new ResponseEntity<>(authService.loginUser(loginRequest), HttpStatus.OK);
    }

    @GetMapping("activate")
    public HttpStatus activateAccount(@RequestParam String token) {

        authService.activateAccount(token);
        return HttpStatus.OK;
    }

    @PostMapping("/checkEmail")
    public HttpStatus sendMailForRecoverPassword(@RequestBody UserRequest userRequest) {

        this.authService.sendMailForRecoverPassword(userRequest.getMail());
        return HttpStatus.OK;
    }

    @PostMapping("/checkToken")
    public HttpStatus checkTokenForRecoverPassword(@Valid @RequestBody RecoverUserPasswordRequest recoverUserPasswordRequest) {

        this.authService.checkTokenForRecoverPassword(recoverUserPasswordRequest);
        return HttpStatus.OK;
    }

    @PostMapping("/recoverPass")
    public HttpStatus recoverPass(@RequestBody RecoverUserPasswordRequest recoverUserPasswordRequest) {

        this.authService.recoverPassword(recoverUserPasswordRequest);
        return HttpStatus.OK;
    }

}
