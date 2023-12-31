package com.example.ckobanksimulator.domain.transactionResponse;

import com.example.ckobanksimulator.domain.paymentResponse.Links;
import com.example.ckobanksimulator.domain.paymentResponse.RiskFactor;


public class TransactionResponse {

    private String outcome;
    private RiskFactor riskFactors;
    private Links _links;

    public TransactionResponse(final String outcome, final Links _links) {
        this.outcome = outcome;
        this._links = _links;
    }

    public TransactionResponse(final String outcome, final RiskFactor riskFactor) {
        this.outcome = outcome;
        this.riskFactors = riskFactor;
    }

    public String getOutcome() {
        return outcome;
    }

    public RiskFactor getRiskFactors() {
        return riskFactors;
    }

    public Links get_links() {
        return _links;
    }
}
