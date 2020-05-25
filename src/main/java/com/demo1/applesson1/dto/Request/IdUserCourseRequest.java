package com.demo1.applesson1.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class IdUserCourseRequest {

    @NotBlank
    private int userId;

    @NotBlank
    private int courseId;
}
