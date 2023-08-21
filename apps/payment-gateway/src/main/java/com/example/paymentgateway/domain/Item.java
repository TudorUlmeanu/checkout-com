package com.example.paymentgateway.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Item {


    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "description must not be blank")
    private String description;

    @Min(value = 1, message = "quantity must be at least 1")
    private Integer quantity;

    @Min(value = 0, message = "price must be positive")
    private Double price;
}
