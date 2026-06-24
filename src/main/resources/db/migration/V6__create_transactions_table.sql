CREATE SEQUENCE transactions_seq START 1 INCREMENT 1;

CREATE TABLE transactions (
                              id             BIGINT PRIMARY KEY DEFAULT nextval('transactions_seq'),
                              origin_id      BIGINT NOT NULL REFERENCES accounts(id),
                              destination_id BIGINT NOT NULL REFERENCES accounts(id),
                              amount         NUMERIC(19,2) NOT NULL,
                              type           VARCHAR(50) NOT NULL,
                              status         VARCHAR(50) NOT NULL,
                              description    VARCHAR(255),
                              created_at     TIMESTAMP DEFAULT NOW()
);