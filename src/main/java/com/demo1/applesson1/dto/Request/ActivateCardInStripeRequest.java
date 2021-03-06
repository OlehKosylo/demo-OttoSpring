package com.demo1.applesson1.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ActivateCardInStripeRequest {

    @NotNull
    private int userId;

    @NotBlank
    private String tokenStripe;

    @NotBlank
    private String cardId;

}
