package com.example.paymentgateway.activemq;

import com.example.paymentgateway.domain.TransactionRequest;
import com.example.paymentgateway.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@EnableJms
public class ActiveMQConsumer {

    private final PaymentService paymentService;

    public ActiveMQConsumer(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @JmsListener(destination = "${amq.transaction.queue}")
    public void consume(TransactionRequest transactionRequest) {
        try {
            log.info("Received message from activeMQ : {}", transactionRequest.getType());
            log.info("Received message from activeMQ : {}", transactionRequest.getTransactionReference());

            paymentService.processTransaction(transactionRequest.getType(), transactionRequest);

            //TODO : Do your thing here
        } catch (Exception e) {
            log.error("Unknown Error occurred in processing CustomMessage", e);
            throw new RuntimeException("Problem in receiving message from Active MQ");
        }
    }
}