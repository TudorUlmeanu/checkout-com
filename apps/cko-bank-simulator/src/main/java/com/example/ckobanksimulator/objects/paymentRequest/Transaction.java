package com.example.ckobanksimulator.objects.paymentRequest;

import com.example.ckobanksimulator.objects.Status;

public class Transaction {

    private String transactionReference;
    private Status status;
    private String currency;
    private Integer amount;
    private String correlationId;

    public Transaction(String transactionReference, Status status, String currency, Integer amount, String correlationId) {
        this.transactionReference = transactionReference;
        this.status = status;
        this.currency = currency;
        this.amount = amount;
        this.correlationId = correlationId;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public Status getStatus() {
        return status;
    }

    public String getCurrency() {
        return currency;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getCorrelationId() {
        return correlationId;
    }
}
