package com.demo1.applesson1.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private int id;
    private int userId;
    private String ownerName;
    private String text;
    private int statusForDeleteView;
}
