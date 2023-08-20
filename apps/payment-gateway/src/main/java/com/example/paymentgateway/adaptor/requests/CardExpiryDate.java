package com.example.paymentgateway.adaptor.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CardExpiryDate {
    private int month;
    private int year;
}