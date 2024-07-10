CREATE DATABASE dbBank;

USE dbBank;

CREATE TABLE facilitator (
    id_facilitator INT AUTO_INCREMENT PRIMARY KEY,
    facilitator_name VARCHAR(50) NOT NULL
);

CREATE TABLE client (
    id_client INT AUTO_INCREMENT PRIMARY KEY,
    client_first_name VARCHAR(50) NOT NULL,
    client_last_name VARCHAR(50) NOT NULL,
    client_direction TEXT NOT NULL,
    client_phone_number VARCHAR(8) NOT NULL
);

CREATE TABLE card (
    id_card INT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(16) NOT NULL,
    expiration_date DATE NOT NULL,
    card_type VARCHAR(50) NOT NULL,
    id_facilitator INT NOT NULL,
    id_client INT NOT NULL,
    CONSTRAINT fk_facilitator FOREIGN KEY (id_facilitator) REFERENCES facilitator(id_facilitator),
    CONSTRAINT fk_client FOREIGN KEY (id_client) REFERENCES client(id_client) ON DELETE CASCADE
);

CREATE TABLE transaction (
    id_transaction INT AUTO_INCREMENT PRIMARY KEY,
    money_amount DECIMAL(10, 2) NOT NULL,
    description TEXT NOT NULL,
    id_card INT NOT NULL,
    transaction_date DATE NOT NULL,
    CONSTRAINT fk_card FOREIGN KEY (id_card) REFERENCES card(id_card) ON DELETE CASCADE
);

INSERT INTO facilitator (facilitator_name) VALUES ('VISA'), ('MasterCard');

INSERT INTO client (client_first_name, client_last_name, client_direction, client_phone_number) VALUES ('Alex', 'Ramirez', 'Lavender Town', '1234567'), ('J', 'P', 'Pisos Picados', '87654321');

INSERT INTO card (card_number, expiration_date, card_type, id_facilitator, id_client) VALUES ('1234567812345678', '2024-12-31', 'Credit', 1, 1), ('8765432187654321', '2025-06-30', 'Debit', 2, 2);

INSERT INTO transaction (money_amount, description, id_card, transaction_date) VALUES (100.00, 'Purchase at Little Caesar', 1, '2024-07-01'), (50.00, 'Purchase at ChatGPT', 1, '2024-04-15'), (2000.00, 'Purchase at Copilot', 2, '2024-07-10'), (100.00, 'Purchase at Bank', 1, '2024-07-20');


INSERT INTO client (client_first_name, client_last_name, client_direction, client_phone_number) 
VALUES 
    ('Mario', 'Bros', 'Mushroom Kingdom', '55555555'),
    ('Luigi', 'Bros', 'Mushroom Kingdom', '44444444'),
    ('Sonic', 'Hedgehog', 'Green Hill Zone', '11111111');


INSERT INTO card (card_number, expiration_date, card_type, id_facilitator, id_client) 
VALUES 
    ('1111222233334444', '2024-09-30', 'Credit', 1, 3),
    ('5555666677778888', '2025-01-31', 'Debit', 2, 4),
    ('9999000011112222', '2024-07-31', 'Credit', 1, 5);

INSERT INTO transaction (money_amount, description, id_card, transaction_date) 
VALUES 
    (500.00, 'Purchase at Amazon', 3, '2024-05-05'),
    (150.00, 'Purchase at eBay', 4, '2024-06-10'),
    (75.00, 'Purchase at Steam', 5, '2024-03-03'),
    (300.00, 'Purchase at Google Play', 3, '2024-02-15'),
    (450.00, 'Purchase at Apple Store', 4, '2024-08-25'),
    (250.00, 'Purchase at Microsoft Store', 5, '2024-01-10');

DROP DATABASE dbbank;
