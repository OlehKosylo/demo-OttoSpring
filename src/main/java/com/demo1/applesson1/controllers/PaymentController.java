package com.demo1.applesson1.controllers;


import com.demo1.applesson1.dto.PaymentRequest;
import com.demo1.applesson1.dto.ActivateCardInStripeRequest;
import com.demo1.applesson1.services.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")

public class PaymentController {

    @Autowired
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("/charge")
    public HttpStatus charge(@RequestBody PaymentRequest paymentRequest) throws StripeException {

        paymentService.chargeCreditCard(paymentRequest);
        return HttpStatus.OK;
    }


    @PostMapping("/activateCard")
    public HttpStatus activateCard(@RequestBody ActivateCardInStripeRequest activateCardInStripeRequest) throws StripeException {

        paymentService.activateCard(
                activateCardInStripeRequest.getTokenStripe(),
                activateCardInStripeRequest.getUserId(),
                activateCardInStripeRequest.getCardId());

        return HttpStatus.OK;
    }

    @PostMapping("/changeCard")
    public HttpStatus changeCard(@RequestBody ActivateCardInStripeRequest activateCardInStripeRequest) throws StripeException {

        paymentService.changeCard(activateCardInStripeRequest.getCardId(), activateCardInStripeRequest.getStripeCustomerId(),
                activateCardInStripeRequest.getTokenStripe(), activateCardInStripeRequest.getUserId());

        return HttpStatus.OK;
    }

}
