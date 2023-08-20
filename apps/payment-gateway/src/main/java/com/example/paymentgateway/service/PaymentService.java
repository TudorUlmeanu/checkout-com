package com.example.paymentgateway.service;

import com.example.paymentgateway.factory.TransactionFactory;
import com.example.paymentgateway.domain.TransactionType;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final TransactionFactory transactionFactory;

    public PaymentService(final TransactionFactory transactionFactory) {
        this.transactionFactory = transactionFactory;
    }

    public void processTransaction(final TransactionType transactionType, Object o) {
        TransactionFactory.getTransaction(transactionType).transact(o);
    }

}
