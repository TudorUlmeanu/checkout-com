package com.example.ckobanksimulator.service;

import com.example.ckobanksimulator.exceptions.TransactionException;
import com.example.ckobanksimulator.domain.Status;
import com.example.ckobanksimulator.domain.paymentRequest.PaymentRequest;
import com.example.ckobanksimulator.domain.paymentRequest.Transaction;
import com.example.ckobanksimulator.repository.TransactionRepository;
import com.example.ckobanksimulator.validator.CardValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    private TransactionRepository transactionRepository;
    private CardValidator cardValidator;

    public TransactionService(final TransactionRepository transactionRepository, final CardValidator cardValidator) {
        this.transactionRepository = transactionRepository;
        this.cardValidator = cardValidator;
    }

    public String processAuthorisation(final PaymentRequest paymentRequest) throws TransactionException {
        if (CardValidator.isValidCreditCard(paymentRequest.getInstruction().getPaymentInstrument().getCardNumber())) {
            return transactionRepository.saveAuthorisation(paymentRequest, computeCorrelationID());
        } else {
            throw new TransactionException("Invalid card number");
        }
    }

    public void processSettle(final String correlationId) throws TransactionException {
        List<Transaction> transactions = transactionRepository.getTransactions(correlationId);

        boolean hasValidAuth = transactions.stream().anyMatch(transaction -> transaction.getStatus().name().equals(Status.AUTHORISED.name()));
        boolean hasBeenResolved = transactions.stream().anyMatch(transaction -> !transaction.getStatus().name().equals(Status.AUTHORISED.name()));

        if (!hasValidAuth) {
            logger.error("No valid authorisation found");
            throw new TransactionException("No valid authorisation found");
        }

        if (hasBeenResolved) {
            logger.error("Transaction already settled");
            throw new TransactionException("Transaction already settled");
        }

        transactionRepository.saveTransaction(Status.SETTLED, transactions.get(0).getTransactionReference(), transactions.get(0).getAmount(), transactions.get(0).getCurrency(), correlationId);
    }

    public void processCancel(final String correlationId) throws TransactionException {
        List<Transaction> transactions = transactionRepository.getTransactions(correlationId);

        boolean hasValidAuth = transactions.stream().anyMatch(transaction -> transaction.getStatus().name().equals(Status.AUTHORISED.name()));
        boolean hasBeenResolved = transactions.stream().anyMatch(transaction -> transaction.getStatus().name().equals(Status.CANCELED.name()) ||
                transaction.getStatus().name().equals(Status.REFUNDED.name()) ||
                transaction.getStatus().name().equals(Status.SETTLED.name()));

        if (!hasValidAuth) {
            logger.error("No valid authorisation found");
            throw new TransactionException("No valid authorisation found");
        }

        if (hasBeenResolved) {
            logger.error("Transaction already resolved");
            throw new TransactionException("Transaction already resolved");
        }

        transactionRepository.saveTransaction(Status.CANCELED, transactions.get(0).getTransactionReference(), transactions.get(0).getAmount(), transactions.get(0).getCurrency(), correlationId);
    }


    public void processRefund(final String correlationId) throws TransactionException {
        List<Transaction> transactions = transactionRepository.getTransactions(correlationId);

        boolean hasValidSettle = transactions.stream().anyMatch(transaction -> !transaction.getStatus().name().equals(Status.SETTLED.name()));
        boolean hasBeenResolved = transactions.stream().anyMatch(transaction -> transaction.getStatus().name().equals(Status.REFUNDED.name()) ||
                transaction.getStatus().name().equals(Status.CANCELED.name()));

        if (hasValidSettle) {
            logger.error("No valid settle found");
            throw new TransactionException("No valid settle found");
        }

        if (hasBeenResolved) {
            logger.error("Transaction already refunded");
            throw new TransactionException("Transaction already refunded");
        }

        transactionRepository.saveTransaction(Status.REFUNDED, transactions.get(0).getTransactionReference(), transactions.get(0).getAmount(), transactions.get(0).getCurrency(), correlationId);
    }

    public List<Transaction> getEvents(final String correlationId) {
        return transactionRepository.getTransactions(correlationId);
    }

    private String computeCorrelationID() {
        final Integer lastIndex = transactionRepository.getLastIndex();
        return "CKO-" + LocalDateTime.now().toString().replaceAll("[^0-9]", "") + '-' + lastIndex;
    }
}