package com.example.paymentgateway.activemq;

import com.example.paymentgateway.domain.TransactionRequest;
import com.example.paymentgateway.service.PaymentService;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@EnableJms
public class ActiveMQConsumer {

    private final PaymentService paymentService;

    public ActiveMQConsumer(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @JmsListener(destination = "${amq.transaction.queue}")
    public void consume(TransactionRequest transactionRequest) {
        try {
            paymentService.processTransaction(transactionRequest.getType(), transactionRequest);
        } catch (final Exception e) {

        }
    }
}