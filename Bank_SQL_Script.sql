DROP TABLE users;
DROP TABLE admins;
DROP TABLE transactions;
DROP TABLE accounts;

CREATE TABLE users(
	user_id SERIAL PRIMARY KEY,
	username VARCHAR(30) NOT NULL,
	pass VARCHAR(30) NOT NULL,
	firstname VARCHAR(30) NOT NULL,
	lastname VARCHAR(30),
	age INTEGER
);

CREATE TABLE admins(
	admin_id SERIAL PRIMARY KEY,
	admin_username VARCHAR(30) NOT NULL,
	admin_password VARCHAR(30) NOT NULL,
	admin_firstname VARCHAR(30) NOT NULL,
	admin_lastname VARCHAR(30)
);

CREATE TABLE transactions(
	transaction_id SERIAL PRIMARY KEY,
	transaction_type VARCHAR(30) NOT NULL,
	transaction_amount REAL,
	user_id INTEGER REFERENCES users(user_id)
);

CREATE TABLE accounts(
	account_id SERIAL PRIMARY KEY,
	account_type VARCHAR(20) NOT NULL,
	balance REAL,
	user_id INTEGER REFERENCES users(user_id)
);