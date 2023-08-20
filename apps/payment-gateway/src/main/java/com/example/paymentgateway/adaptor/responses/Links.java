package com.example.paymentgateway.adaptor.responses;

import lombok.*;

import java.net.URL;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Links {

    private Map<String, URL> links;
}