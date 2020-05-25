package com.demo1.applesson1.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class CommentRequest {
    private long courseId;

    @NotBlank
    private int userId;

    @NotBlank
    private String text;
}
