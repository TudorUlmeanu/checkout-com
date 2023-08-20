package com.example.ckobanksimulator.repository;

import com.example.ckobanksimulator.domain.paymentRequest.PaymentRequest;
import com.example.ckobanksimulator.domain.Status;
import com.example.ckobanksimulator.domain.paymentRequest.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepository {

    private final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);

    private final JdbcTemplate jdbcTemplate;

    private final String INSERT_TRANSACTION = "INSERT INTO transaction (status, transactionReference, merchant, paymentType, currency, amount, correlationId) \n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String SELECT_LAST_ID = "SELECT COALESCE(MAX(id), 0) + 1 AS last_id FROM transaction";
    private final String SELECT_ALL_TRANSACTIONS = "SELECT s.status AS status, t.status, t.transactionReference AS transactionReference, t.currency AS currency, t.amount AS amount, t.correlationId AS correlationId FROM transaction t\n" +
            "JOIN status s on t.status = s.id\n" +
            "WHERE correlationId = ?";

    @Autowired
    public TransactionRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String saveAuthorisation(final PaymentRequest paymentRequest, final String correlationId) {
        jdbcTemplate.update(INSERT_TRANSACTION,
                Status.AUTHORISED.getValue(),
                paymentRequest.getTransactionReference(),
                paymentRequest.getMerchant().getEntity(),
                paymentRequest.getInstruction().getPaymentInstrument().getType(),
                paymentRequest.getInstruction().getValue().getCurrency(),
                paymentRequest.getInstruction().getValue().getAmount(),
                correlationId
        );

        logger.info(Status.AUTHORISED.name() + " transaction persisted");

        return correlationId;
    }

    public List<Transaction> getTransactions(final String correlationId) {
        return jdbcTemplate.query(SELECT_ALL_TRANSACTIONS, new Object[]{correlationId}, (rs, rowNum) -> {
            Transaction transaction = new Transaction(
                    rs.getString("transactionReference"),
                    Status.valueOf(rs.getString("status")),
                    rs.getString("currency"),
                    rs.getInt("amount"),
                    rs.getString("correlationId"));

            return transaction;
        });

    }

    public Integer getLastIndex() {
        return jdbcTemplate.queryForObject(SELECT_LAST_ID, Integer.class);
    }

    public void saveTransaction(final Status status, final String transactionReference, final Integer amount, final String currency, final String correlationId) {
        jdbcTemplate.update(INSERT_TRANSACTION,
                status.getValue(),
                transactionReference,
                null,
                null,
                currency,
                amount,
                correlationId
        );

        logger.info(status.name() + " transaction persisted");
    }
}