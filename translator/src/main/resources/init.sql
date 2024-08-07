CREATE TABLE user (
    id              SERIAL          PRIMARY KEY,
    ip_address      VARCHAR,
    source_text     TEXT,
    target_text     TEXT,
    created         TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);