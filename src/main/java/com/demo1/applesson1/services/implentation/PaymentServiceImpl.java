package com.demo1.applesson1.services.implentation;

import com.demo1.applesson1.models.User;
import com.demo1.applesson1.repository.UserRepository;
import com.demo1.applesson1.services.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;


@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;

    private static final String TEST_STRIPE_SECRET_KEY = "sk_test_eL13nvKqdoSOSKp87jAcPXFI007qcoFUWg";

    public PaymentServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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

        } catch (Exception e) {
        }

        return id;
    }

    @Override
    public void chargeCreditCard(int userId, int amount) {

        Order order = new Order();
        order.setAmount((long) amount);
        order.setCustomer(getStripeCustomerId(userId));

        int chargeAmountCents = (int) (amount * 100);

        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", chargeAmountCents);
        chargeParams.put("currency", "usd");
        chargeParams.put("description", "Otto - Customer, price: " + amount);
        chargeParams.put("customer", getStripeCustomerId(userId));

        try {
            // Submit charge to credit card
            Charge charge = Charge.create(chargeParams);
            System.out.println(charge);
        } catch (Exception e) {
        }
    }

    @Override
    public Card activateCard(String tok_visa, int userId) throws StripeException {
        Stripe.apiKey = TEST_STRIPE_SECRET_KEY;

        Customer customer =
                Customer.retrieve(getStripeCustomerId(userId));

        Map<String, Object> params = new HashMap<>();
        params.put("source", tok_visa);

        Card card = (Card) customer.getSources().create(params);

        setStripeCard(userId, tok_visa);
        return card;
    }

    @Override
    public String getStripeCustomerId(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", id)));

        return user.getStripeCustomerId();
    }

    @Override
    public void setStripeCard(int userId, String cardId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", userId)));

        User updateUser = User.builder()
                .id(user.getId())
                .mail(user.getMail())
                .lvl(user.getLvl())
                .password(user.getPassword())
                .username(user.getUsername())
                .sex(user.getSex())
                .age(user.getAge())
                .name_surname(user.getName_surname())
                .roles(user.getRoles())
                .courses(user.getCourses())
                .stripeCustomerId(user.getStripeCustomerId())
                .stripeCardId(cardId)
                .build();

        userRepository.save(updateUser);
    }

}
