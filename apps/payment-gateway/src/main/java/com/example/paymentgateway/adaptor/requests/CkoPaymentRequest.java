package com.example.paymentgateway.adaptor.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CkoPaymentRequest {

    private String transactionReference;
    private Merchant merchant;
    private Instruction instruction;
}