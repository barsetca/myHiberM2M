DROP TABLE IF EXISTS purchases;
DROP TABLE IF EXISTS customers;



CREATE TABLE IF NOT EXISTS customers
(
    id   bigserial PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS purchases
(
    id    bigserial PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    cost  INTEGER,
    date  date default NOW(),
    id_customer bigint NOT NULL,
    FOREIGN KEY (id_customer) REFERENCES customers (id) ON DELETE CASCADE
);
