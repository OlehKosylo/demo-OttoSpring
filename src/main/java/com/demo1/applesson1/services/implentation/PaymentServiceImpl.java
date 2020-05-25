package com.demo1.applesson1.services.implentation;

import com.demo1.applesson1.dto.Request.ActivateCardInStripeRequest;
import com.demo1.applesson1.dto.Response.CardResponse;
import com.demo1.applesson1.dto.Request.PaymentRequest;
import com.demo1.applesson1.models.Course;
import com.demo1.applesson1.models.User;
import com.demo1.applesson1.repository.CourseRepository;
import com.demo1.applesson1.repository.UserRepository;
import com.demo1.applesson1.services.PaymentService;
import com.stripe.Stripe;
import com.stripe.model.*;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;


@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    //    @Value("${stripe.key.secret}")
    private static final String TEST_STRIPE_SECRET_KEY = "sk_test_eL13nvKqdoSOSKp87jAcPXFI007qcoFUWg";

    public PaymentServiceImpl(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        Stripe.apiKey = TEST_STRIPE_SECRET_KEY;
    }


    @Override
    public String createCustomer(User user) {

        Map<String, Object> customerParams = new HashMap<String, Object>();
        customerParams.put("description", user.getName_surname());
        customerParams.put("email", user.getMail());

        String id = null;

        try {
            Customer stripeCustomer = Customer.create(customerParams);
            id = stripeCustomer.getId();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return id;
    }

    @Override
    public void chargeCreditCard(PaymentRequest paymentRequest) {

        int[] money = countMoney(paymentRequest.getPrice(), 10);

        String[] monthAndYear = yearAndMonthForCard(paymentRequest);

        Map<String, Object> card = new HashMap<>();
        card.put("number", paymentRequest.getCard_number());
        card.put("exp_month", monthAndYear[0]);
        card.put("exp_year", monthAndYear[1]);
        card.put("cvc", paymentRequest.getCard_cvc());
        Map<String, Object> params = new HashMap<>();
        params.put("type", "card");
        params.put("card", card);

        try {
            PaymentMethod paymentMethod =
                    PaymentMethod.create(params);

            String paymentId = paymentMethod.getId();

            PaymentIntent intent = PaymentIntent.create(PaymentIntentCreateParams.builder()
                    .setAmount((long) money[0])
                    .setCurrency("usd")
                    .setPaymentMethod(paymentId)
                    // A PaymentIntent can be confirmed some time after creation,
                    // but here we want to confirm (collect payment) immediately.
                    .setConfirm(true)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        int courseId = paymentRequest.getCourseId();
        Course courseObject = this.courseRepository.findById((long) courseId).orElseThrow(() -> new RuntimeException(String.format("Course with id: %s not found!", courseId)));
        User user = courseObject.getUser();

        String stripeCustomerId = user.getStripeCustomerId();

        try {
            Customer customer =
                    Customer.retrieve(stripeCustomerId);

            Map<String, Object> paramsForCustomer = new HashMap<>();
            paramsForCustomer.put("amount", -money[1]);
            paramsForCustomer.put("currency", "usd");

            CustomerBalanceTransaction balanceTransaction =
                    customer.balanceTransactions().create(paramsForCustomer);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void activateCard(ActivateCardInStripeRequest activateCardInStripeRequest) {

        String tok_visa = activateCardInStripeRequest.getTokenStripe();
        int userId = activateCardInStripeRequest.getUserId();
        String cardId = activateCardInStripeRequest.getCardId();


        try {
            Customer customer =
                    Customer.retrieve(getStripeCustomerId(userId));

            Map<String, Object> params = new HashMap<>();
            params.put("source", tok_visa);

            Card card = (Card) customer.getSources().create(params);

            setStripeCard(userId, tok_visa, cardId);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String getStripeCustomerId(int id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", id)));

        return user.getStripeCustomerId();
    }


    @Override
    public void setStripeCard(int userId, String tokenStripe, String cardId) {

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
                .tokenStripe(tokenStripe)
                .photoURL(user.getPhotoURL())
                .stripeCardId(cardId)
                .statusMailActivate(user.getStatusMailActivate())
                .build();

        userRepository.save(updateUser);
    }

    @Override
    public void changeCard(String cardId, String stripeCustomerId, String tokenId, int userId) {
        try {
            Customer customer =
                    Customer.retrieve(stripeCustomerId);

            Card card =
                    (Card) customer.getSources().retrieve(cardId);

            Card deletedCard = (Card) card.delete();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public CardResponse retrieveCard(int userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException(String.format("User with id: %s not found!", userId)));

        try {
            Customer customer =
                    Customer.retrieve(user.getStripeCustomerId());

            Card card =
                    (Card) customer.getSources().retrieve(
                            user.getStripeCardId()
                    );


            return CardResponse.builder()
                    .exp_month(card.getExpMonth())
                    .exp_year(Math.toIntExact(card.getExpYear()))
                    .last4(card.getLast4())
                    .name(card.getName())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    private int[] countMoney(int amount, int procent) {
        int procentOfMoney = (amount * procent);
        int moneyForCustomer = (amount - procentOfMoney) * 10;
        return new int[]{procentOfMoney, moneyForCustomer};
    }

    private String[] yearAndMonthForCard(PaymentRequest paymentRequest) {
        String card_expiry = paymentRequest.getCard_expiry();
        String[] split = card_expiry.split("/");
        String month;
        if (split[0].trim().charAt(0) == '0') {
            month = split[0].trim().charAt(1) + "";
        } else {
            month = split[0].trim();
        }

        String year;

        if (split[1].trim().length() == 2) {
            year = "20" + split[1].trim();
        } else {
            year = split[1].trim();
        }

        String[] array = new String[2];
        array[0] = month;
        array[1] = year;

        return array;
    }

}
