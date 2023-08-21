package com.example.paymentgateway.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Merchant {

//    @JsonProperty("merchantName")
    @NotBlank(message = "merchantName must not be blank")
    private String merchantName;

}
