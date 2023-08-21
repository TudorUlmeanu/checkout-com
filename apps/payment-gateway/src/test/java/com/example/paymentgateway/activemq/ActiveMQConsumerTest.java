package com.example.paymentgateway.activemq;

import com.example.paymentgateway.domain.TransactionRequest;
import com.example.paymentgateway.domain.TransactionType;
import com.example.paymentgateway.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

public class ActiveMQConsumerTest {

    @Mock
    private PaymentService paymentService;

    private ActiveMQConsumer activeMQConsumer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        activeMQConsumer = new ActiveMQConsumer(paymentService);
    }

    @Test
    public void shouldConsumeSuccessfully() {
        TransactionRequest transactionRequest = new TransactionRequest(TransactionType.SETTLE, "reference");

        activeMQConsumer.consume(transactionRequest);

        verify(paymentService, times(1)).processTransaction(transactionRequest.getType(), transactionRequest);
    }

    @Test
    public void ShouldConsumeException() {
        TransactionRequest transactionRequest =new TransactionRequest(TransactionType.SETTLE, "reference");
        doThrow(new RuntimeException("Test exception")).when(paymentService).processTransaction(any(), any());

        activeMQConsumer.consume(transactionRequest);

        verify(paymentService, times(1)).processTransaction(transactionRequest.getType(), transactionRequest);
    }
}