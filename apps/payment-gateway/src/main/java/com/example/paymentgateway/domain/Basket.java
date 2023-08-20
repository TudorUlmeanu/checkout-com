package com.example.paymentgateway.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Basket {

    @NotNull
    @Valid
    private List<Item> items;

    @Min(value = 0, message = "amount must be positive")
    private Double amount;
}
