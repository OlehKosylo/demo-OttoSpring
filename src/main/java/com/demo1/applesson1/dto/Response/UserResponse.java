package com.demo1.applesson1.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Integer id;

    private String username;

    private String name_surname;

    private int age;

    private String sex;

    private int lvl;

    private String mail;

    private String stripe_card_id;

    private String stripe_customer_id;

    private String token_stripe;

    private CardResponse card;

    private String photoURL;
}

