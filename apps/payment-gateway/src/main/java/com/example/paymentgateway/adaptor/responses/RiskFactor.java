package com.example.paymentgateway.adaptor.responses;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskFactor {

    private String risk;
    private String type;
    private String detail;

}
