package com.demo1.applesson1.dto;

import lombok.*;

@Data
@Getter
@Setter
public class EditUserInfoRequest {
    private int id;
    private String name_surname;
    private int age;
    private String sex;
    private String password;
}


