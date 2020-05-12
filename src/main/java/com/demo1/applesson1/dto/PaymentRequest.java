package com.demo1.applesson1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private int userId;
    private String token;
    private int amount;
}
