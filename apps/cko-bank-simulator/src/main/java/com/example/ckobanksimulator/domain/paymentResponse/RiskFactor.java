package com.example.ckobanksimulator.domain.paymentResponse;

public class RiskFactor {

    private String risk;
    private String type;
    private String detail;

    public RiskFactor(String risk, String type, String detail) {
        this.risk = risk;
        this.type = type;
        this.detail = detail;
    }

    public String getRisk() {
        return risk;
    }

    public String getType() {
        return type;
    }

    public String getDetail() {
        return detail;
    }
}
