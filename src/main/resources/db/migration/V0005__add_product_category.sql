CREATE TABLE product_category (
    id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   name VARCHAR(255) NOT NULL,
   restaurant_id UUID NOT NULL,
   CONSTRAINT pk_productcategory PRIMARY KEY (id)
);

CREATE TABLE product_product_category (
  product_category_id UUID NOT NULL,
   product_id UUID NOT NULL
);

ALTER TABLE product_category ADD CONSTRAINT FK_PRODUCTCATEGORY_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE product_product_category ADD CONSTRAINT fk_proprocat_on_product FOREIGN KEY (product_category_id) REFERENCES product (id);

ALTER TABLE product_product_category ADD CONSTRAINT fk_proprocat_on_product_category FOREIGN KEY (product_id) REFERENCES product_category (id);

CREATE INDEX idx_product_category_name ON product_category(name);