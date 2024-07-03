CREATE TABLE accounts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE,
    money INTEGER NOT NULL
);

INSERT INTO accounts(user_id, money)
VALUES
    (1, 10000),
    (2, 0),
    (3, 999900);