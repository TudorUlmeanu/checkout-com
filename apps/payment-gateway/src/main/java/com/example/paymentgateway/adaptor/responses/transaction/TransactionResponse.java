package com.example.paymentgateway.adaptor.responses.transaction;

import com.example.paymentgateway.adaptor.responses.Links;
import com.example.paymentgateway.adaptor.responses.RiskFactor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {

    private String outcome;
    private RiskFactor riskFactors;
    private Links _links;

}