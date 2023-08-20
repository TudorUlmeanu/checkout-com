package com.example.ckobanksimulator.objects.paymentRequest;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class Merchant {

    @JsonProperty("entity")
    @NotBlank(message = "merchant.entity must not be blank")
    private String entity;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}
