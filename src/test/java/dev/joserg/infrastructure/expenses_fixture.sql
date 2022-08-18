TRUNCATE expense;
TRUNCATE friend;
INSERT INTO friend (id, name)
VALUES ('0968aa6a-043b-4b25-9dbb-7bc3b6226523', 'Raúl González'),
       ('3fe21d23-0dff-482c-adca-c64f3bd8c226', 'Francisco Buyo'),
       ('c218b11f-24b2-4a55-ab4a-1504cb7b3ca1', 'Jose María Gutiérrez'),
       ('e18656bc-3902-4f39-9a49-17a556a5fbae', 'Alfonso Pérez');
INSERT INTO expenses.expense (id, payer_id, amount, description, created_at)
VALUES (1, '3fe21d23-0dff-482c-adca-c64f3bd8c226', 10000, 'Cena', '2022-08-16 17:21:32'),
       (2, 'e18656bc-3902-4f39-9a49-17a556a5fbae', 1000, 'Taxi', '2022-08-16 17:22:08'),
       (3, 'e18656bc-3902-4f39-9a49-17a556a5fbae', 5340, 'Compra', '2022-08-16 17:22:28');