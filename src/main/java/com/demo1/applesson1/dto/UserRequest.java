package com.demo1.applesson1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private String username;
    private String password;
    private String name_surname;
    private int age;
    private String sex;
    private String mail;
}
