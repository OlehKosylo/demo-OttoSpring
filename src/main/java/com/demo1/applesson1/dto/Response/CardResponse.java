package com.demo1.applesson1.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {

    @NotBlank
    private Long exp_month;

    @NotBlank
    private Integer exp_year;

    @NotBlank
    private String last4;

    @NotBlank
    private String name;
}
