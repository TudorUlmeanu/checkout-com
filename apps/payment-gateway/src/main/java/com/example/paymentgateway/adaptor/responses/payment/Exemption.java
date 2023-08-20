package com.example.paymentgateway.adaptor.responses.payment;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exemption {

    private String result;
    private String reason;

}