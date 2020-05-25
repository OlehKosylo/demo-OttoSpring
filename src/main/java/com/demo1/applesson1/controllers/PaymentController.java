package com.demo1.applesson1.controllers;


import com.demo1.applesson1.dto.Request.ChangeCardInStripeRequest;
import com.demo1.applesson1.dto.Request.PaymentRequest;
import com.demo1.applesson1.dto.Request.ActivateCardInStripeRequest;
import com.demo1.applesson1.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/payment")

public class PaymentController {

    @Autowired
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @PostMapping("/charge")
    public HttpStatus charge(@Valid @RequestBody PaymentRequest paymentRequest) {

        paymentService.chargeCreditCard(paymentRequest);
        return HttpStatus.OK;
    }


    @PostMapping("/activateCard")
    public HttpStatus activateCard(@Valid @RequestBody ActivateCardInStripeRequest activateCardInStripeRequest) {

        paymentService.activateCard(activateCardInStripeRequest);

        return HttpStatus.OK;
    }

    @PostMapping("/changeCard")
    public HttpStatus changeCard(@Valid @RequestBody ChangeCardInStripeRequest changeCardInStripeRequest) {

        paymentService.changeCard(changeCardInStripeRequest);

        return HttpStatus.OK;
    }

}
