package com.example.paymentgateway.repository;

import com.example.paymentgateway.adaptor.responses.payment.PaymentResponse;
import com.example.paymentgateway.domain.PaymentRequest;
import com.example.paymentgateway.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    private final String SELECT_CORRELATION_ID = "SELECT correlationId FROM transaction WHERE transactionReference = ?";
    private final String UPDATE_TRANSACTION_SETTLED = "UPDATE transaction SET status = ? WHERE transactionReference = ?";
    private final String SELECT_LAST_ID = "SELECT COALESCE(MAX(id), 0) + 1 AS last_id FROM transaction";
    private final String UPDATE_TRANSACTION = "UPDATE transaction\n" +
            "SET status = ?, correlationId = ?, settle_url = ?, refund_url = ?, cancel_url = ?, event_url = ?\n" +
            "WHERE transactionReference = ?";
    private final String INSERT_TRANSACTION = "INSERT INTO transaction (status, transactionReference, merchant, paymentMethod, currency, amount) \n" +
            "VALUES (?, ?, ?, ?, ?, ?)";

    @Autowired
    public TransactionRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer getLastIndex() {
        return jdbcTemplate.queryForObject(SELECT_LAST_ID, Integer.class);
    }

    public void saveAuthorisation(final PaymentRequest paymentRequest, final String transactionReference) {
        jdbcTemplate.update(INSERT_TRANSACTION,
                Status.UNKNOWN.name(),
                transactionReference,
                paymentRequest.getMerchant().getMerchantName(),
                paymentRequest.getPaymentMethod(),
                paymentRequest.getCurrency(),
                paymentRequest.getTotalAmount()
        );
    }

    public void consolidateTransaction(final Status status, final PaymentResponse paymentResponse, final String transactionReference) {
        jdbcTemplate.update(UPDATE_TRANSACTION,
                status.name(),
                paymentResponse.getIssuer(),
                paymentResponse.get_links().getLinks().get("Settle").toString(),
                paymentResponse.get_links().getLinks().get("Refund").toString(),
                paymentResponse.get_links().getLinks().get("Cancel").toString(),
                paymentResponse.get_links().getLinks().get("Events").toString(),
                transactionReference
        );
    }

    public String getCorrelationId(final String transactionReference) {
        return jdbcTemplate.queryForObject(SELECT_CORRELATION_ID, new Object[]{transactionReference}, String.class).replaceAll(" ", "");
    }

    public void updateTransaction(final String status, final String transactionReference) {
        jdbcTemplate.update(UPDATE_TRANSACTION_SETTLED, status, transactionReference);
    }
}
