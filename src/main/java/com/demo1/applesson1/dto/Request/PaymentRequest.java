package com.demo1.applesson1.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PaymentRequest {

    @NotBlank
    private String card_number;

    private String first_name;

    private String last_name;

    @NotBlank
    private String card_expiry;

    @NotBlank
    private String card_cvc;

    @NotBlank
    private int courseId;

    @NotBlank
    private int price;
}
