package com.demo1.applesson1.controllers;


import com.demo1.applesson1.dto.PaymentRequest;
import com.demo1.applesson1.services.PaymentService2;
import com.demo1.applesson1.services.StripeClient;
import com.stripe.model.Charge;
import com.stripe.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")

public class PaymentController {

    @Autowired
    private final StripeClient stripeClient;

    @Autowired
    private final PaymentService2 paymentService2;

    public PaymentController(StripeClient stripeClient, PaymentService2 paymentService2) {
        this.stripeClient = stripeClient;
        this.paymentService2 = paymentService2;
    }


    @PostMapping("/charge")
    public HttpEntity<Charge> chargeCard(@RequestBody PaymentRequest paymentRequest) throws Exception {

        String token = paymentRequest.getToken();
        int amount = paymentRequest.getAmount();

        return new ResponseEntity<>(this.stripeClient.chargeCreditCard(token, amount), HttpStatus.OK);
    }


    @PostMapping("/test")
    public void test(){
        Order order = new Order();
        order.setAmount((long) 1000);
        order.setCustomer("cus_HGX1v472PJzJss");

        paymentService2.chargeCreditCard(order);
    }

}

