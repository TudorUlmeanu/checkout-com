package com.example.paymentgateway.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardExpiryDate {

    @Min(value = 1, message = "Month must be between 1 and 12")
    @Max(value = 12, message = "Month must be between 1 and 12")
    @JsonProperty("month")
    private int month;

    @Min(value = 2023, message = "Year must be 2023 or later")
    @Max(value = 2100, message = "Year must be before 2100")
    @JsonProperty("year")
    private int year;

}