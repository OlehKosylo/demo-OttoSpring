package com.demo1.applesson1.services;


import com.demo1.applesson1.models.User;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;


public interface PaymentService {
    String createCustomer(User user);

    void chargeCreditCard(int userId, int amount);

    Card activateCard(String tok_visa, int userId) throws StripeException;

    String getStripeCustomerId(int userId);

    void setStripeCard(int userId, String cardId);

}
