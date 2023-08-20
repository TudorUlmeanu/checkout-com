package com.example.ckobanksimulator.objects.paymentRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class PaymentRequest {

    @NotBlank(message = "transactionReference must not be blank")
    @JsonProperty("transactionReference")
    private String transactionReference;

    @Valid
    @NotNull
    @JsonProperty("merchant")
    private Merchant merchant;

    @Valid
    @NotNull
    @JsonProperty("instruction")
    private Instruction instruction;


    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }
}