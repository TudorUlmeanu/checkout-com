package com.example.ckobanksimulator.domain.paymentRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


public class Value {

    @JsonProperty("currency")
    @NotBlank(message = "value.currency must not be blank")
    private String currency;

    @JsonProperty("amount")
    @Min(value = 0, message = "value.amount must not be blank")
    private Double amount;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}