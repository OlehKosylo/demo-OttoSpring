package com.demo1.applesson1.services;

import com.demo1.applesson1.dto.UserRequest;
import com.demo1.applesson1.models.User;
import com.stripe.model.Order;

public interface PaymentService2 {
     String createCustomer(User user);
     void chargeCreditCard(Order order);
}
