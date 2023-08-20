package com.example.paymentgateway.factory;

import com.example.paymentgateway.domain.TransactionType;

public interface Transaction<T> {

    TransactionType getType();

    void transact(T object);
}