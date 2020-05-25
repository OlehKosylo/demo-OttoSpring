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
public class UserResponse {

    @NotBlank
    private Integer id;

    @NotBlank
    private String username;

    @NotBlank
    private String name_surname;

    private int age;

    private String sex;

    @NotBlank
    private int lvl;

    @NotBlank
    private String mail;

    @NotBlank
    private String stripe_card_id;

    @NotBlank
    private String stripe_customer_id;

    @NotBlank
    private String token_stripe;

    @NotBlank
    private CardResponse card;

    @NotBlank
    private String photoURL;
}

