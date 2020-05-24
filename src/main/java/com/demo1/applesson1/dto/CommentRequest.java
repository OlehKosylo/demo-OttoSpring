package com.demo1.applesson1.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentRequest {
    private long courseId;
    private int userId;
    private String text;
}
