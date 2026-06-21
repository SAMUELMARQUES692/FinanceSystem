CREATE TABLE users_scopes (
    user_id  BIGINT NOT NULL REFERENCES users(id),
    scope_id BIGINT NOT NULL REFERENCES scopes(id),
    PRIMARY KEY (user_id, scope_id)
);