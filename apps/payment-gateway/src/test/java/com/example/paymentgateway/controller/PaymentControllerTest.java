package com.example.paymentgateway.controller;

import com.example.paymentgateway.domain.*;
import com.example.paymentgateway.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(PaymentController.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void shouldReturnOK() throws Exception {
        Merchant merchant = new Merchant();
        merchant.setMerchantName("Test Shop");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .paymentMethod("Card")
                .merchant(merchant)
                .basket(Basket.builder().amount(12.0).items(List.of(Item.builder().name("Item").price(12.0).description("Test item").quantity(1).build())).build())
                .cardDetails(Card.builder().cardNumber("1111222233334444").cvv("123").cardExpiryDate(CardExpiryDate.builder().month(11).year(2024).build()).build())
                .currency("GBP")
                .totalAmount(12.0)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(paymentRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldReturnBadRequestWhenInvalidPaymentRequest() throws Exception {
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .paymentMethod("Card")
                .merchant(null)
                .basket(Basket.builder().amount(12.0).items(List.of(Item.builder().name("Item").price(12.0).description("Test item").quantity(1).build())).build())
                .cardDetails(Card.builder().cardNumber("1111222233334444").cvv("123").cardExpiryDate(CardExpiryDate.builder().month(11).year(2024).build()).build())
                .currency("GBP")
                .totalAmount(12.0)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(paymentRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldReturnTransactions() throws Exception {
        String transactionReference = "PGW-123";

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(transactionReference, "AUTHORISED", "GBP", 12.0, "someId"));

        when(paymentService.getTransactions(transactionReference)).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/events/{transactionReference}", transactionReference))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(asJsonString(transactions)));

        verify(paymentService, times(1)).getTransactions(transactionReference);
    }


    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}