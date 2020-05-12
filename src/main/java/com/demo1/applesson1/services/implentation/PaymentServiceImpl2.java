package com.demo1.applesson1.services.implentation;

import com.demo1.applesson1.dto.UserRequest;
import com.demo1.applesson1.models.User;
import com.demo1.applesson1.services.PaymentService2;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("paymentService")
public class PaymentServiceImpl2 implements PaymentService2 {

    private static final String TEST_STRIPE_SECRET_KEY = "sk_test_eL13nvKqdoSOSKp87jAcPXFI007qcoFUWg";

    public PaymentServiceImpl2() {
        Stripe.apiKey = TEST_STRIPE_SECRET_KEY;
    }


    @Override
    public String createCustomer(User user) {

        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("description", user.getName_surname());
        customerParams.put("email", user.getMail());

        String id = null;

        try {
            // Create customer
            Customer stripeCustomer = Customer.create(customerParams);
            id = stripeCustomer.getId();
            System.out.println(stripeCustomer);

        } catch (Exception e) {}

        return id;
    }

    @Override
    public void chargeCreditCard(Order order) {

        // Stripe requires the charge amount to be in cents
        int chargeAmountCents = (int) (12000);

//        Customer user = order.getCustomerObject();

        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", chargeAmountCents);
        chargeParams.put("currency", "usd");
        chargeParams.put("description", "Monthly Charges");
        chargeParams.put("customer", "cus_HGXCzwfxDruaiQ");

        try {
            // Submit charge to credit card
            Charge charge = Charge.create(chargeParams);
            System.out.println(charge);
        } catch (Exception e) {}
    }
}
