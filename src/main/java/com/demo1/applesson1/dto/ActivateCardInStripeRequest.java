package com.demo1.applesson1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActivateCardInStripeRequest {
    private int userId;
    private String tokenStripe;
    private int amount;
    private String cardId;
    private String stripeCustomerId;
}
