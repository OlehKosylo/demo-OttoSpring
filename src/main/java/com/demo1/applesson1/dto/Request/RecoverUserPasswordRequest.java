package com.demo1.applesson1.dto.Request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RecoverUserPasswordRequest {

    @NonNull
    private int userId;

    @NotBlank
    private String token;

    private String password;
}
