ALTER TABLE customer_order ADD quantity BIGINT DEFAULT 1;

ALTER TABLE customer_order ADD product_id UUID NOT NULL;

ALTER TABLE customer_order ADD status VARCHAR(255) NOT NULL;

ALTER TABLE customer_order ADD CONSTRAINT FK_CUSTOMERORDER_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

DROP TABLE product_order_info;