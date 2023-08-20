package com.example.ckobanksimulator.domain.paymentRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class PaymentInstrument {


    @JsonProperty("type")
    @NotBlank(message = "paymentInstrument.type must not be blank")
    private String type;

    @JsonProperty("cardNumber")
    @NotBlank(message = "paymentInstrument.cardNumber must not be blank")
    private String cardNumber;

    @NotNull
    @Valid
    @JsonProperty("cardExpiryDate")
    private CardExpiryDate cardExpiryDate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public CardExpiryDate getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(CardExpiryDate cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

}