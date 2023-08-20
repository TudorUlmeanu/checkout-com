package com.example.paymentgateway.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Card {

    @NotBlank
    private String cardNumber;

    @Valid
    @NotNull
    private CardExpiryDate cardExpiryDate;

    //TODO : check if its 3 digits
    private Integer cvv;

}
