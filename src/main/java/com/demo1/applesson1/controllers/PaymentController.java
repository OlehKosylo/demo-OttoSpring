package com.demo1.applesson1.controllers;


import com.demo1.applesson1.dto.PaymentRequest;
import com.demo1.applesson1.services.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void charge(@RequestParam int userId, @RequestParam int amount ){

        paymentService.chargeCreditCard(userId,amount);
    }

    @PostMapping("/activateCard")
    public void activateCard(@RequestBody PaymentRequest paymentRequest) throws StripeException {
        paymentService.activateCard(paymentRequest.getToken(), paymentRequest.getUserId());
    }

}

