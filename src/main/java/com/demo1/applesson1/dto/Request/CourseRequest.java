package com.demo1.applesson1.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class CourseRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private int price;

    @NotBlank
    private String genre;

    @NotBlank
    private String downloadURL;

    @NotNull
    private int userId;
}
