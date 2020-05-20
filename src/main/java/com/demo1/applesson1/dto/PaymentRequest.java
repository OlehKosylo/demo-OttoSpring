package com.demo1.applesson1.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequest {
    private String card_number;
    private String first_name;
    private String last_name;
    private String card_expiry;
    private String card_cvc;
    private int courseId;
    private int price;
}
