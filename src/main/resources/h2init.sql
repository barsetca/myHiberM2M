DROP TABLE IF EXISTS products_customers;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS products;


CREATE TABLE IF NOT EXISTS customers
(
    id   bigint AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE
    );

CREATE TABLE IF NOT EXISTS products
(
    id    bigint AUTO_INCREMENT PRIMARY KEY ,
    title VARCHAR(255) UNIQUE,
    cost  INTEGER
   );

CREATE TABLE IF NOT EXISTS products_customers
(
    id_product  bigint REFERENCES products (id),
    id_customer bigint REFERENCES customers (id)

);

