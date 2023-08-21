package com.example.paymentgateway.adaptor;

import com.example.paymentgateway.adaptor.requests.*;
import com.example.paymentgateway.adaptor.responses.payment.PaymentResponse;
import com.example.paymentgateway.adaptor.responses.transaction.TransactionResponse;
import com.example.paymentgateway.domain.PaymentRequest;
import com.example.paymentgateway.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class CkoBankAdaptor {

    @Value("${cko-bank-simulator.url}")
    private String url;

    private final RestTemplate restTemplate;

    @Autowired
    public CkoBankAdaptor(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<PaymentResponse> execute(final PaymentRequest paymentRequest, final String transactionReference) {
        return restTemplate.postForEntity(url + "/authorisation", buildRequest(paymentRequest, transactionReference), PaymentResponse.class);
    }

    public ResponseEntity<TransactionResponse> execute(final String correlationId) {
        return restTemplate.postForEntity(url + "/transaction/settle/" + correlationId, null, TransactionResponse.class);
    }

    public ResponseEntity<List<Transaction>> executeEvents(final String correlationId) {
        return restTemplate.exchange(url + "/transaction/events/" + correlationId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Transaction>>() {});
    }

    private HttpEntity<CkoPaymentRequest> buildRequest(final PaymentRequest paymentRequest, final String transactionReference) {
        final Narrative narrative = new Narrative("some narative");
        final com.example.paymentgateway.adaptor.requests.Value value = new com.example.paymentgateway.adaptor.requests.Value(paymentRequest.getCurrency(), paymentRequest.getTotalAmount());
        final CardExpiryDate cardExpiryDate = new CardExpiryDate(paymentRequest.getCardDetails().getCardExpiryDate().getMonth(), paymentRequest.getCardDetails().getCardExpiryDate().getYear());
        final PaymentInstrument paymentInstrument = new PaymentInstrument(paymentRequest.getPaymentMethod(), paymentRequest.getCardDetails().getCardNumber(), cardExpiryDate);
        final Instruction instruction = new Instruction(narrative, value, paymentInstrument);
        final Merchant merchant = new Merchant(paymentRequest.getMerchant().getMerchantName());

        CkoPaymentRequest request = new CkoPaymentRequest(transactionReference, merchant, instruction);

        return new HttpEntity<>(request);
    }
}
