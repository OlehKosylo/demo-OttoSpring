package com.demo1.applesson1.dto;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private long id;
    private String title;
    private String description;
    private int price;
    private String genre;
    private String downloadURL;
    private int statusForCheckIfUserHasThisCourse;
}