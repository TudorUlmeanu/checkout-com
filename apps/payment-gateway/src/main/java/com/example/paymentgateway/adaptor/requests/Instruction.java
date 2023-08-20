package com.example.paymentgateway.adaptor.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Instruction {

    private Narrative narrative;
    private Value value;
    private PaymentInstrument paymentInstrument;

}