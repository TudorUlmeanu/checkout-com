package com.example.ckobanksimulator.objects.paymentResponse;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Links {

    private Map<String, URL> links = new HashMap<>();

    public void addCancel(final String token) throws MalformedURLException {
        links.put("Cancel", new URL("https://try.access.cko-bank-simulator.com/payments/transaction/cancel" + token));
    }

    public void addSettle(final String token) throws MalformedURLException {
        links.put("Settle", new URL("https://try.access.cko-bank-simulator.com/payments/transaction/settle" + token));
    }

    public void addRefund(final String token) throws MalformedURLException {
        links.put("Refund", new URL("https://try.access.cko-bank-simulator.com/payments/transaction/refund" + token));
    }

    public void addEvents(final String token) throws MalformedURLException {
        links.put("Events", new URL("https://try.access.cko-bank-simulator.com/payments/transaction/events/" + token));
    }

    public Map<String, URL> getLinks() {
        return links;
    }
}