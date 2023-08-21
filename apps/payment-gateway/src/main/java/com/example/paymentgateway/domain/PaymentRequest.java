package com.example.paymentgateway.domain;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentRequest {

    @NotNull
    @Valid
    private Merchant merchant;

    @NotNull
    @Valid
    private Basket basket;

    private Card cardDetails;

    @NotBlank(message = "paymentMethod must not be blank")
    private String paymentMethod;

    //TODO : check if its 3 letters and it really exists
    @NotBlank(message = "currency must not be blank")
    private String currency;

    @Min(value = 0, message = "totalAmount must be positive")
    private Double totalAmount;

}
