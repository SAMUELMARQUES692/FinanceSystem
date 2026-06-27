CREATE SEQUENCE emails_seq START 1 INCREMENT 1;

CREATE TABLE emails (
    id BIGINT PRIMARY KEY DEFAULT nextval('emails_seq'),
    user_id BIGINT NOT NULL,
    email_from VARCHAR(255) NOT NULL,
    email_to VARCHAR(255) NOT NULL,
    email_subject VARCHAR(255) NOT NULL,
    body TEXT,
    sent_at TIMESTAMP DEFAULT NOW(),
    status_email VARCHAR(50) NOT NULL
);