package com.demo1.applesson1.controllers;

import com.demo1.applesson1.configuration.PaypalConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/paypal")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaypalController {

    private final PaypalConfig payPalClient;

    @PostMapping(value = "/make/payment")
    public Map<String, Object> makePayment(@RequestParam String sum) {
        return payPalClient.createPayment(sum);
    }

    @PostMapping(value = "/complete/payment")
    public Map<String, Object> completePayment(@RequestParam String paymentId, @RequestParam String payerId) {
        System.out.println(paymentId + payerId);
        return payPalClient.completePayment(paymentId, payerId);
    }
}

