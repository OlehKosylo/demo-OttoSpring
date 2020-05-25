package com.demo1.applesson1.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class IdUserCourseRequest {

    @NotNull
    private int userId;

    @NotNull
    private int courseId;
}
