package com.demo1.applesson1.dto.Response;

import com.demo1.applesson1.models.Commentaries;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    @NotNull
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private int price;

    private String genre;

    @NotBlank
    private String downloadURL;

    @NotNull
    private int statusForCheckIfUserHasThisCourse;


    private List<Commentaries> commentaries;
}
