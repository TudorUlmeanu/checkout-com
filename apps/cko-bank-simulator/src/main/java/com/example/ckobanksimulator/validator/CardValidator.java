package com.example.ckobanksimulator.validator;

import org.springframework.stereotype.Component;

@Component
public class CardValidator {

    public static boolean isValidCreditCard(final String card) {
        String cardNumber = card.replaceAll("\\s+", "");

        if (!cardNumber.matches("\\d{13,19}")) {
            return false;
        }

        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }

        return sum % 10 == 0;
    }
}
