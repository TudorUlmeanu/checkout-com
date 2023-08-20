package com.example.ckobanksimulator.domain.paymentRequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


public class Instruction {

    @JsonProperty("narrative")
    private Narrative narrative;

    @NotNull
    @Valid
    @JsonProperty("value")
    private Value value;

    @NotNull
    @Valid
    @JsonProperty("paymentInstrument")
    private PaymentInstrument paymentInstrument;


    public Narrative getNarrative() {
        return narrative;
    }

    public void setNarrative(Narrative narrative) {
        this.narrative = narrative;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public PaymentInstrument getPaymentInstrument() {
        return paymentInstrument;
    }

    public void setPaymentInstrument(PaymentInstrument paymentInstrument) {
        this.paymentInstrument = paymentInstrument;
    }
}