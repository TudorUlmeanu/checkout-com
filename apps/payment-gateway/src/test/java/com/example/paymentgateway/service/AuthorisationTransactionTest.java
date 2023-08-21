package com.example.paymentgateway.service;

import com.example.paymentgateway.activemq.ActiveMQProducer;
import com.example.paymentgateway.adaptor.CkoBankAdaptor;
import com.example.paymentgateway.adaptor.responses.payment.PaymentResponse;
import com.example.paymentgateway.domain.*;
import com.example.paymentgateway.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.*;

public class AuthorisationTransactionTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ActiveMQProducer activeMQProducer;

    @Mock
    private CkoBankAdaptor ckoBankAdaptor;

    private AuthorisationTransaction authorisationTransaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authorisationTransaction = new AuthorisationTransaction(transactionRepository, activeMQProducer, ckoBankAdaptor);
    }

    @Test
    public void shouldProcessSuccessfulAuthorisation() {
        PaymentRequest paymentRequest = createSamplePaymentRequest();
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setOutcome(Status.AUTHORISED.name());

        when(ckoBankAdaptor.execute(any(), any())).thenReturn(ResponseEntity.ok(paymentResponse));
        when(transactionRepository.getLastIndex()).thenReturn(1);

        authorisationTransaction.transact(paymentRequest);

        verify(transactionRepository, times(1)).saveAuthorisation(paymentRequest, "PGW-merchant-1");
        verify(transactionRepository, times(1)).consolidateTransaction(Status.AUTHORISED, paymentResponse, "PGW-merchant-1");
        verify(activeMQProducer, times(1)).publish(any());
    }

    @Test
    public void shouldProcessFailedAuthorisation() {
        PaymentRequest paymentRequest = createSamplePaymentRequest();
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setOutcome(Status.REFUSED.name());

        when(ckoBankAdaptor.execute(any(), any())).thenReturn(ResponseEntity.ok(paymentResponse));
        when(transactionRepository.getLastIndex()).thenReturn(1);

        authorisationTransaction.transact(paymentRequest);

        verify(transactionRepository, times(1)).saveAuthorisation(paymentRequest, "PGW-merchant-1");
        verify(transactionRepository, never()).consolidateTransaction(any(), any(), any());
        verify(activeMQProducer, never()).publish(any());
    }

    private PaymentRequest createSamplePaymentRequest() {
        Merchant merchant = new Merchant();
        merchant.setMerchantName("merchant");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .paymentMethod("Card")
                .merchant(merchant)
                .basket(Basket.builder().amount(12.0).items(List.of(Item.builder().name("Item").price(12.0).description("Test item").quantity(1).build())).build())
                .cardDetails(Card.builder().cardNumber("1111222233334444").cvv("123").cardExpiryDate(CardExpiryDate.builder().month(11).year(2024).build()).build())
                .currency("GBP")
                .totalAmount(12.0)
                .build();
        return paymentRequest;
    }
}