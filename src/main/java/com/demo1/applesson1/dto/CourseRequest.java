package com.demo1.applesson1.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
public class CourseRequest {
    private String title;
    private String description;
    private int price;
    private String genre;
    private String downloadURL;
    private int userId;
}
