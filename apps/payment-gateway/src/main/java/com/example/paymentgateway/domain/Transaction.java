package com.example.paymentgateway.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private String transactionReference;
    private String status;
    private String currency;
    private Double amount;
    private String correlationId;
}
