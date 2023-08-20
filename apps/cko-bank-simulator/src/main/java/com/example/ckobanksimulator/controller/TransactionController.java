package com.example.ckobanksimulator.controller;

import com.example.ckobanksimulator.exceptions.TransactionException;
import com.example.ckobanksimulator.objects.Status;
import com.example.ckobanksimulator.objects.transactionResponse.TransactionResponse;
import com.example.ckobanksimulator.objects.paymentRequest.Transaction;
import com.example.ckobanksimulator.objects.paymentResponse.Links;
import com.example.ckobanksimulator.objects.paymentResponse.RiskFactor;
import com.example.ckobanksimulator.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/settle/{correlationId}")
    public ResponseEntity<TransactionResponse> settleTransaction(@PathVariable String correlationId) {
        try {
            transactionService.processSettle(correlationId);

            logger.info("Settle transaction processed");

            return new ResponseEntity<>(new TransactionResponse(Status.SETTLED.name(), getLinksSettle(correlationId)), HttpStatus.OK);
        } catch (TransactionException e) {
            logger.error("Settle transaction failed: " + e.getMessage());
            return new ResponseEntity<>(new TransactionResponse(Status.REFUSED.name(), new RiskFactor("not_checked", "error", e.getMessage())), HttpStatus.OK);
        }
    }

    @PostMapping("/cancel/{correlationId}")
    public ResponseEntity<TransactionResponse> cancelTransaction(@PathVariable String correlationId) {
        try {
            transactionService.processCancel(correlationId);

            logger.info("Cancel transaction processed");

            return new ResponseEntity<>(new TransactionResponse(Status.CANCELED.name(), getLinksCancelRefund(correlationId)), HttpStatus.OK);
        } catch (TransactionException e) {
            logger.error("Cancel transaction failed: " + e.getMessage());
            return new ResponseEntity<>(new TransactionResponse(Status.REFUSED.name(), new RiskFactor("not_checked", "error", e.getMessage())), HttpStatus.OK);
        }
    }

    @PostMapping("/refund/{correlationId}")
    public ResponseEntity<TransactionResponse> refundTransaction(@PathVariable String correlationId) {
        try {
            transactionService.processRefund(correlationId);

            logger.info("Refund transaction processed");

            return new ResponseEntity<>(new TransactionResponse(Status.REFUNDED.name(), getLinksCancelRefund(correlationId)), HttpStatus.OK);
        } catch (TransactionException e) {
            logger.error("Refund transaction failed: " + e.getMessage());
            return new ResponseEntity<>(new TransactionResponse(Status.REFUSED.name(), new RiskFactor("not_checked", "error", e.getMessage())), HttpStatus.OK);
        }
    }

    @GetMapping("/events/{correlationId}")
    public ResponseEntity<List<Transaction>> eventTransactions(@PathVariable String correlationId) {
        List<Transaction> transactions = transactionService.getEvents(correlationId);
        logger.info("Events retrieved successfully");

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    private Links getLinksSettle(String correlationId) {
        try {
            Links links = new Links();
            links.addRefund(correlationId);
            links.addEvents(correlationId);

            return links;
        } catch (MalformedURLException e) {
            return new Links();
        }
    }

    private Links getLinksCancelRefund(String correlationId) {
        try {
            Links links = new Links();
            links.addEvents(correlationId);

            return links;
        } catch (MalformedURLException e) {
            return new Links();
        }
    }
}
