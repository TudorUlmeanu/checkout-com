package com.example.paymentgateway.service;

import com.example.paymentgateway.adaptor.CkoBankAdaptor;
import com.example.paymentgateway.adaptor.responses.transaction.TransactionResponse;
import com.example.paymentgateway.domain.Status;
import com.example.paymentgateway.factory.Transaction;
import com.example.paymentgateway.domain.TransactionRequest;
import com.example.paymentgateway.domain.TransactionType;
import com.example.paymentgateway.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class SettleTransaction implements Transaction<TransactionRequest> {

    private static final TransactionType SETTLE_TYPE = TransactionType.SETTLE;

    private final TransactionRepository transactionRepository;
    private final CkoBankAdaptor ckoBankAdaptor;

    public SettleTransaction(final TransactionRepository transactionRepository, final CkoBankAdaptor ckoBankAdaptor) {
        this.transactionRepository = transactionRepository;
        this.ckoBankAdaptor = ckoBankAdaptor;
    }

    @Override
    public void transact(final TransactionRequest transactionRequest) {
        String correlationId = transactionRepository.getCorrelationId(transactionRequest.getTransactionReference());
        TransactionResponse transactionResponse = ckoBankAdaptor.execute(correlationId).getBody();

        if(transactionResponse.getOutcome().equals(Status.SETTLED.name())) {
            transactionRepository.updateTransaction(Status.SETTLED.name(), transactionRequest.getTransactionReference());
        }

    }

    @Override
    public TransactionType getType() {
        return SETTLE_TYPE;
    }

}
