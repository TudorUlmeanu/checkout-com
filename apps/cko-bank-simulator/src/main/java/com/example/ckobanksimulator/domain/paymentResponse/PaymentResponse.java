package com.example.ckobanksimulator.domain.paymentResponse;

import java.util.List;

public class PaymentResponse {

    private String outcome;
    private List<RiskFactor> riskFactors;
    private Exemption exemption;
    private String issuer;
    private Links _links;

    public PaymentResponse(String outcome, List<RiskFactor> riskFactors, Exemption exemption, String issuer, Links _links) {
        this.outcome = outcome;
        this.riskFactors = riskFactors;
        this.exemption = exemption;
        this.issuer = issuer;
        this._links = _links;
    }

    public PaymentResponse(String outcome, String issuer, Links _links) {
        this.outcome = outcome;
        this.issuer = issuer;
        this._links = _links;
    }

    public PaymentResponse(String outcome, List<RiskFactor> riskFactors) {
        this.outcome = outcome;
        this.riskFactors = riskFactors;
    }

    public PaymentResponse(String outcome) {
        this.outcome = outcome;
    }

    public String getOutcome() {
        return outcome;
    }

    public List<RiskFactor> getRiskFactors() {
        return riskFactors;
    }

    public Exemption getExemption() {
        return exemption;
    }

    public String getIssuer() {
        return issuer;
    }

    public Links get_links() {
        return _links;
    }

}
