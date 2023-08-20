CREATE TABLE status
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(16)
);


CREATE TABLE merchant
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    merchantName  VARCHAR(16),
    currency      VARCHAR(3),
    paymentMethod VARCHAR(16)
);

CREATE TABLE transaction
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    status               VARCHAR(16),
    transactionReference VARCHAR(255),
    correlationId        VARCHAR(255),
    merchant             VARCHAR(16),
    currency             VARCHAR(16),
    paymentMethod        VARCHAR(16),
    amount               DOUBLE PRECISION,
    settle_url           VARCHAR(255),
    refund_url           VARCHAR(255),
    cancel_url           VARCHAR(255),
    event_url            VARCHAR(255)
);
