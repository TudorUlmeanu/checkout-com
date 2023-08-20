package com.example.paymentgateway.controller;

import com.example.paymentgateway.domain.PaymentRequest;
import com.example.paymentgateway.domain.TransactionType;
import com.example.paymentgateway.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/authorisation")
    public void payment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.processTransaction(TransactionType.AUTHORISE, paymentRequest);
    }

    @GetMapping("/events")
    public void payment(@PathVariable String transactionReference) {

    }
}
