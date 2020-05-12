package com.demo1.applesson1.services;

import com.stripe.model.Charge;
import com.stripe.model.Customer;

public interface StripeClient {
    Charge chargeCreditCard(String token, double amount) throws Exception;

    Customer createCustomer(String token, String email) throws Exception;

    Charge chargeCustomerCard(String customerId, int amount) throws Exception;

}
