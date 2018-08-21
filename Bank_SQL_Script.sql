DROP TABLE users;
DROP TABLE transactions;
DROP TABLE accounts;

-- CREATE TABLE accounts(
-- 	account_id SERIAL PRIMARY KEY,
-- 	account_type VARCHAR(20) NOT NULL,
-- 	balance REAL,
-- 	user_id INTEGER REFERENCES users(user_id)
-- );