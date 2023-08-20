package com.example.paymentgateway.adaptor.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Value {

    private String currency;
    private Double amount;

}