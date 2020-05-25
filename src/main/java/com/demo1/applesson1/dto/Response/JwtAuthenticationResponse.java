package com.demo1.applesson1.dto.Response;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class JwtAuthenticationResponse {

    @NotBlank
    private String accessToken;

    @NotBlank
    private String tokenType = "Bearer";

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
