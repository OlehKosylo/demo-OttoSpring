package com.demo1.applesson1.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class CourseRequest {

    private String title;

    private String description;

    private int price;

    private String genre;

    private String downloadURL;

    @NotBlank
    private int userId;
}
