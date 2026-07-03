CREATE DATABASE IF NOT EXISTS atmdb;
USE atmdb;

CREATE TABLE IF NOT EXISTS users (
    card_number VARCHAR(20) PRIMARY KEY,
    pin VARCHAR(10) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL DEFAULT 0.00
);

CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(20) NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (card_number) REFERENCES users(card_number)
);

-- Insert default user if not exists
INSERT INTO users (card_number, pin, balance)
SELECT '1234567890123456', '2025', 5000.00
WHERE NOT EXISTS (SELECT 1 FROM users WHERE card_number = '1234567890123456');

-- Update root password
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
FLUSH PRIVILEGES;
