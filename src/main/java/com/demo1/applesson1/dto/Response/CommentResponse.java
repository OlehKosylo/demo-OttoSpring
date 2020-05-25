package com.demo1.applesson1.dto.Response;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    @NotBlank
    private int id;

    @NotBlank
    private int userId;

    @NotBlank
    private String ownerName;

    @NotBlank
    private String text;

    @NotBlank
    private int statusForDeleteView;
}
