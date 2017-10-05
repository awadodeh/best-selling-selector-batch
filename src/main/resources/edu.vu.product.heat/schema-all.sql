DROP TABLE products;

CREATE TABLE products (
  product_id    BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  product_name  VARCHAR(40),
  product_price BIGINT,
  product_rank  BIGINT
);