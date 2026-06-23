CREATE SEQUENCE accounts_seq START 1 INCREMENT 1;

CREATE TABLE accounts (
    id         BIGINT PRIMARY KEY DEFAULT nextval('accounts_seq'),
    user_id    BIGINT NOT NULL REFERENCES users(id),
    balance    NUMERIC(19,2) NOT NULL DEFAULT 0,
    agency     VARCHAR(10) NOT NULL,
    number     VARCHAR(20) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT NOW()
);