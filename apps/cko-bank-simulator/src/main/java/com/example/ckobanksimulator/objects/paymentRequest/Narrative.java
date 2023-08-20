package com.example.ckobanksimulator.objects.paymentRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;


public class Narrative {

    @JsonProperty("line1")
    @NotBlank(message = "narrative.line1 must not be blank")
    public String line1;

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }
}
