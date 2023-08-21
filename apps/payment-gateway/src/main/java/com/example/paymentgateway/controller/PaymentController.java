package com.example.paymentgateway.controller;

import com.example.paymentgateway.domain.PaymentRequest;
import com.example.paymentgateway.domain.Transaction;
import com.example.paymentgateway.domain.TransactionType;
import com.example.paymentgateway.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/authorisation")
    public ResponseEntity<HttpStatus> payment(@Valid @RequestBody final PaymentRequest paymentRequest) {
        paymentService.processTransaction(TransactionType.AUTHORISE, paymentRequest);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/events/{transactionReference}")
    public ResponseEntity<List<Transaction>> payment(@PathVariable final String transactionReference) {
        return new ResponseEntity<>(paymentService.getTransactions(transactionReference), HttpStatus.OK);
    }
}
