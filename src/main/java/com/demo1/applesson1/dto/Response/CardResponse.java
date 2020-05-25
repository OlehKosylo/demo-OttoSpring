package com.demo1.applesson1.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {

    @NotNull
    private Long exp_month;

    @NotNull
    private Integer exp_year;

    @NotBlank
    private String last4;

    @NotBlank
    private String name;
}
