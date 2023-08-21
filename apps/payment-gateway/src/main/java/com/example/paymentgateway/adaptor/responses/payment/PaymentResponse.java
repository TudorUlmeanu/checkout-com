package com.example.paymentgateway.adaptor.responses.payment;

import com.example.paymentgateway.adaptor.responses.Links;
import com.example.paymentgateway.adaptor.responses.RiskFactor;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String outcome;
    private List<RiskFactor> riskFactors;
    private Exemption exemption;
    private String issuer;
    private Links _links;

}
