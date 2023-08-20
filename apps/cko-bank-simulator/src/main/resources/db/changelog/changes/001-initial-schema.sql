CREATE TABLE status
(
    id     INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(16)
);

CREATE TABLE transaction
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    status               INT,
    transactionReference VARCHAR(255),
    merchant             VARCHAR(255),
    paymentType          VARCHAR(10),
    currency             VARCHAR(3),
    amount               INT,
    correlationId        VARCHAR(32)
);