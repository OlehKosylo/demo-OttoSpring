package com.demo1.applesson1.dto.Response;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    @NotNull
    private int id;

    @NotNull
    private int userId;

    @NotBlank
    private String ownerName;

    @NotBlank
    private String text;

    @NotNull
    private int statusForDeleteView;
}
