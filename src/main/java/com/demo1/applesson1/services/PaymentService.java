package com.demo1.applesson1.services;


import com.demo1.applesson1.dto.CardResponse;
import com.demo1.applesson1.dto.PaymentRequest;
import com.demo1.applesson1.models.User;
import com.stripe.exception.StripeException;


public interface PaymentService {
    String createCustomer(User user);

    void chargeCreditCard(PaymentRequest paymentRequest) throws StripeException;

    void activateCard(String tok_visa, int userId, String cardId) throws StripeException;

    String getStripeCustomerId(int userId);

    void setStripeCard(int userId, String tok_vise, String cardId);

    void changeCard(String cardId, String stripeCustomerId, String tokenId, int userId) throws StripeException;

    CardResponse retrieveCard (int userId) throws StripeException;

}
