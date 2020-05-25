package com.demo1.applesson1.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRequest {
    private int id;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String name_surname;

    @NotNull
    private int age;

    @NotBlank
    private String sex;

    @NotBlank
    private String mail;

    @NotBlank
    private String photoURL;
}
