package com.demo1.applesson1.dto.Response;

import com.demo1.applesson1.models.Commentaries;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {

    @NotBlank
    private long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private int price;

    private String genre;

    @NotBlank
    private String downloadURL;

    @NotBlank
    private int statusForCheckIfUserHasThisCourse;

    @NotBlank
    private List<Commentaries> commentaries;
}
