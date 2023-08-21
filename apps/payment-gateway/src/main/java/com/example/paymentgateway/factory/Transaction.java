package com.example.paymentgateway.factory;

import com.example.paymentgateway.domain.TransactionType;

public interface Transaction<T> {

    TransactionType getType();

    String transact(T object);
}