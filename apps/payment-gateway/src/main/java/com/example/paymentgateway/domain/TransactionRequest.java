package com.example.paymentgateway.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonSerialize
@JsonDeserialize
public class TransactionRequest implements Serializable {

    private TransactionType type;
    private String transactionReference;

}
