package com.example.paymentgateway.adaptor.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PaymentInstrument {

    private String type;
    private String cardNumber;
    private CardExpiryDate cardExpiryDate;
}