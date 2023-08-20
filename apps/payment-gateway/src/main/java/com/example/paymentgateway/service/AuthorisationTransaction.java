package com.example.paymentgateway.service;

import com.example.paymentgateway.activemq.ActiveMQProducer;
import com.example.paymentgateway.adaptor.CkoBankAdaptor;
import com.example.paymentgateway.adaptor.responses.payment.PaymentResponse;
import com.example.paymentgateway.factory.Transaction;
import com.example.paymentgateway.domain.PaymentRequest;
import com.example.paymentgateway.domain.Status;
import com.example.paymentgateway.domain.TransactionRequest;
import com.example.paymentgateway.domain.TransactionType;
import com.example.paymentgateway.repository.TransactionRepository;
import org.springframework.stereotype.Component;

@Component
public class AuthorisationTransaction implements Transaction<PaymentRequest> {

    private static final TransactionType AUTHORISATION_TYPE = TransactionType.AUTHORISE;

    private final TransactionRepository transactionRepository;
    private final ActiveMQProducer activeMQProducer;
    private final CkoBankAdaptor ckoBankAdaptor;

    public AuthorisationTransaction(final TransactionRepository transactionRepository, final ActiveMQProducer activeMQProducer, final CkoBankAdaptor ckoBankAdaptor) {
        this.transactionRepository = transactionRepository;
        this.activeMQProducer = activeMQProducer;
        this.ckoBankAdaptor = ckoBankAdaptor;
    }


    @Override
    public void transact(final PaymentRequest paymentRequest) {
        final String transactionReference = generateTransactionReference(paymentRequest.getMerchant().getMerchantName());

        transactionRepository.saveAuthorisation(paymentRequest, transactionReference);
        PaymentResponse response = ckoBankAdaptor.execute(paymentRequest, transactionReference).getBody();

        if (response.getOutcome().equals(Status.AUTHORISED.name())) {
            transactionRepository.consolidateTransaction(Status.AUTHORISED, response, transactionReference);
            activeMQProducer.publish(new TransactionRequest(TransactionType.SETTLE, transactionReference));
        }
    }

    private String generateTransactionReference(final String merchant) {
        return "PGW-" + merchant.replaceAll("[^a-zA-Z]", "").toLowerCase() + "-" + transactionRepository.getLastIndex();
    }

    @Override
    public TransactionType getType() {
        return AUTHORISATION_TYPE;
    }
}
