package com.example.paymentgateway.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Merchant {

    @NotBlank(message = "merchantName must not be blank")
    private String merchantName;

}
