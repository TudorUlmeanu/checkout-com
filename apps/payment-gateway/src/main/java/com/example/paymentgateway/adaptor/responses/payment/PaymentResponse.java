package com.example.paymentgateway.adaptor.responses.payment;

import com.example.paymentgateway.adaptor.responses.Links;
import com.example.paymentgateway.adaptor.responses.RiskFactor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
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
