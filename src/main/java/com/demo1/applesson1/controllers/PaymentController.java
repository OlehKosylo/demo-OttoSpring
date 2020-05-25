package com.demo1.applesson1.controllers;


import com.demo1.applesson1.dto.Request.PaymentRequest;
import com.demo1.applesson1.dto.Request.ActivateCardInStripeRequest;
import com.demo1.applesson1.services.PaymentService;
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
    public HttpStatus charge(@RequestBody PaymentRequest paymentRequest) {

        paymentService.chargeCreditCard(paymentRequest);
        return HttpStatus.OK;
    }


    @PostMapping("/activateCard")
    public HttpStatus activateCard(@RequestBody ActivateCardInStripeRequest activateCardInStripeRequest) {

        paymentService.activateCard(activateCardInStripeRequest);

        return HttpStatus.OK;
    }

    @PostMapping("/changeCard")
    public HttpStatus changeCard(@RequestBody ActivateCardInStripeRequest activateCardInStripeRequest) {

        paymentService.changeCard(activateCardInStripeRequest.getCardId(), activateCardInStripeRequest.getStripeCustomerId(),
                activateCardInStripeRequest.getTokenStripe(), activateCardInStripeRequest.getUserId());

        return HttpStatus.OK;
    }

}
