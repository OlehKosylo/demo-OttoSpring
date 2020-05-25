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
public class UserResponse {

    @NotNull
    private Integer id;

    @NotBlank
    private String username;

    @NotBlank
    private String name_surname;

    private int age;

    private String sex;

    @NotNull
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

