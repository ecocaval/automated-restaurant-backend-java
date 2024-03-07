CREATE TABLE address (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   zip_code VARCHAR(8) NOT NULL,
   name VARCHAR(100) NOT NULL,
   number VARCHAR(6),
   neighborhood VARCHAR(50) NOT NULL,
   complement VARCHAR(50),
   city VARCHAR(255) NOT NULL,
   ibge_code VARCHAR(7),
   state VARCHAR(255) NOT NULL,
   restaurant_id UUID NOT NULL,
   CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE bill (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   restaurant_table_id UUID NOT NULL,
   CONSTRAINT pk_bill PRIMARY KEY (id)
);

CREATE TABLE collaborator (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   name VARCHAR(255) NOT NULL,
   job_title_id UUID NOT NULL,
   restaurant_id UUID NOT NULL,
   CONSTRAINT pk_collaborator PRIMARY KEY (id)
);

CREATE TABLE customer (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   name VARCHAR(255) NOT NULL,
   email VARCHAR(255) NOT NULL,
   cell_phone_area_code VARCHAR(2) NOT NULL,
   cell_phone VARCHAR(9) NOT NULL,
   restaurant_id UUID NOT NULL,
   CONSTRAINT pk_customer PRIMARY KEY (id)
);

CREATE TABLE customer_order (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   customer_id UUID NOT NULL,
   bill_id UUID NOT NULL,
   restaurant_table_id UUID,
   CONSTRAINT pk_customerorder PRIMARY KEY (id)
);

CREATE TABLE job_title (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   name VARCHAR(255) NOT NULL,
   permission VARCHAR(255) NOT NULL,
   restaurant_id UUID NOT NULL,
   CONSTRAINT pk_jobtitle PRIMARY KEY (id)
);

CREATE TABLE product (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   active BOOLEAN DEFAULT TRUE NOT NULL,
   name VARCHAR(255) NOT NULL,
   serving_capacity BIGINT NOT NULL,
   sku BIGINT NOT NULL,
   description VARCHAR(255) NOT NULL,
   price DOUBLE PRECISION NOT NULL,
   restaurant_id UUID NOT NULL,
   CONSTRAINT pk_product PRIMARY KEY (id)
);

CREATE TABLE product_image (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   product_id UUID NOT NULL,
   priority BIGINT NOT NULL,
   CONSTRAINT pk_productimage PRIMARY KEY (id)
);

CREATE TABLE product_order_info (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   quantity BIGINT NOT NULL,
   product_id UUID NOT NULL,
   customer_order_id UUID NOT NULL,
   CONSTRAINT pk_productorderinfo PRIMARY KEY (id)
);

CREATE TABLE restaurant (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   active BOOLEAN DEFAULT TRUE NOT NULL,
   name VARCHAR(255) NOT NULL,
   CONSTRAINT pk_restaurant PRIMARY KEY (id)
);

CREATE TABLE restaurant_image (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   restaurant_id UUID NOT NULL,
   is_logo BOOLEAN NOT NULL,
   priority BIGINT,
   CONSTRAINT pk_restaurantimage PRIMARY KEY (id)
);

CREATE TABLE restaurant_table (
  id UUID NOT NULL,
   creation_date TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
   last_modified_date TIMESTAMP WITHOUT TIME ZONE,
   deleted BOOLEAN DEFAULT FALSE NOT NULL,
   identification VARCHAR(255) NOT NULL,
   capacity BIGINT NOT NULL,
   status VARCHAR(255) NOT NULL,
   restaurant_id UUID NOT NULL,
   CONSTRAINT pk_restauranttable PRIMARY KEY (id)
);

ALTER TABLE restaurant_table ADD CONSTRAINT uc_cc2a73cad852a50bf8c119865 UNIQUE (identification, restaurant_id);

ALTER TABLE restaurant_table ADD CONSTRAINT FK_RESTAURANTTABLE_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE restaurant_image ADD CONSTRAINT FK_RESTAURANTIMAGE_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE product_order_info ADD CONSTRAINT FK_PRODUCTORDERINFO_ON_CUSTOMER_ORDER FOREIGN KEY (customer_order_id) REFERENCES customer_order (id);

ALTER TABLE product_order_info ADD CONSTRAINT FK_PRODUCTORDERINFO_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE product_image ADD CONSTRAINT FK_PRODUCTIMAGE_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES product (id);

ALTER TABLE product ADD CONSTRAINT FK_PRODUCT_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE job_title ADD CONSTRAINT uc_9ef3e73ffc36dc52bc97ca80b UNIQUE (name, restaurant_id);

ALTER TABLE job_title ADD CONSTRAINT FK_JOBTITLE_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE customer_order ADD CONSTRAINT FK_CUSTOMERORDER_ON_BILL FOREIGN KEY (bill_id) REFERENCES bill (id);

ALTER TABLE customer_order ADD CONSTRAINT FK_CUSTOMERORDER_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE customer_order ADD CONSTRAINT FK_CUSTOMERORDER_ON_RESTAURANT_TABLE FOREIGN KEY (restaurant_table_id) REFERENCES restaurant_table (id);

ALTER TABLE customer ADD CONSTRAINT FK_CUSTOMER_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE collaborator ADD CONSTRAINT FK_COLLABORATOR_ON_JOB_TITLE FOREIGN KEY (job_title_id) REFERENCES job_title (id);

ALTER TABLE collaborator ADD CONSTRAINT FK_COLLABORATOR_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

ALTER TABLE bill ADD CONSTRAINT FK_BILL_ON_RESTAURANT_TABLE FOREIGN KEY (restaurant_table_id) REFERENCES restaurant_table (id);

ALTER TABLE address ADD CONSTRAINT FK_ADDRESS_ON_RESTAURANT FOREIGN KEY (restaurant_id) REFERENCES restaurant (id);

