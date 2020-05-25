package com.demo1.applesson1.dto.Request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ActivateCardInStripeRequest {

    private int userId;

    @NotBlank
    private String tokenStripe;

    private int amount;

    @NotBlank
    private String cardId;

    @NotBlank
    private String stripeCustomerId;
}
