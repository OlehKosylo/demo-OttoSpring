package com.demo1.applesson1.services;

import com.demo1.applesson1.dto.Request.RecoverUserPasswordRequest;
import com.demo1.applesson1.dto.Response.JwtAuthenticationResponse;
import com.demo1.applesson1.dto.Request.LoginRequest;
import com.demo1.applesson1.dto.Request.UserRequest;

public interface AuthService {

    void registerUser(UserRequest userRequest);

    JwtAuthenticationResponse loginUser(LoginRequest loginRequest);

    void sendMailForRecoverPassword (String email);

    void checkTokenForRecoverPassword(RecoverUserPasswordRequest recoverUserPasswordRequest);

    void recoverPassword(RecoverUserPasswordRequest recoverUserPasswordRequest);

    void activateAccount(String token);
}
