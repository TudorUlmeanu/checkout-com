package com.example.paymentgateway.factory;

import com.example.paymentgateway.domain.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TransactionFactory {
    private static Map<TransactionType, Transaction> TransactionMap;

    @Autowired
    private TransactionFactory(List<Transaction> Transactions) {
        TransactionMap =    Transactions.stream().collect(Collectors.toUnmodifiableMap(Transaction::getType, Function.identity()));
    }

    public static <T> Transaction<T> getTransaction(TransactionType TransactionType) {
        return   Optional.ofNullable(TransactionMap.get(TransactionType)).orElseThrow(IllegalArgumentException::new);
    }
}