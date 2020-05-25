package com.demo1.applesson1.services;


import com.demo1.applesson1.dto.Request.ActivateCardInStripeRequest;
import com.demo1.applesson1.dto.Response.CardResponse;
import com.demo1.applesson1.dto.Request.PaymentRequest;
import com.demo1.applesson1.models.User;


public interface PaymentService {
    String createCustomer(User user);

    String getStripeCustomerId(int userId);

    void chargeCreditCard(PaymentRequest paymentRequest);

    void activateCard(ActivateCardInStripeRequest activateCardInStripeRequest);

    void setStripeCard(int userId, String tok_vise, String cardId);

    void changeCard(String cardId, String stripeCustomerId, String tokenId, int userId);

    CardResponse retrieveCard (int userId);

}
