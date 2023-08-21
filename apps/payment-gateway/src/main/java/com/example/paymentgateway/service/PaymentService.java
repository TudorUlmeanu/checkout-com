package com.example.paymentgateway.service;

import com.example.paymentgateway.adaptor.CkoBankAdaptor;
import com.example.paymentgateway.domain.Transaction;
import com.example.paymentgateway.factory.TransactionFactory;
import com.example.paymentgateway.domain.TransactionType;
import com.example.paymentgateway.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final TransactionFactory transactionFactory;
    private final TransactionRepository transactionRepository;
    private final CkoBankAdaptor ckoBankAdaptor;

    public PaymentService(final TransactionFactory transactionFactory, final TransactionRepository transactionRepository, final CkoBankAdaptor ckoBankAdaptor) {
        this.transactionFactory = transactionFactory;
        this.transactionRepository = transactionRepository;
        this.ckoBankAdaptor = ckoBankAdaptor;
    }

    public String processTransaction(final TransactionType transactionType, Object o) {
        return TransactionFactory.getTransaction(transactionType).transact(o);
    }

    public List<Transaction> getTransactions(final String transactionReference) {
        String correlationId = transactionRepository.getCorrelationId(transactionReference);
        return ckoBankAdaptor.executeEvents(correlationId).getBody();
    }

}
