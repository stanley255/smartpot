CREATE TABLE PRODUCT_TYPE(
  id    INT PRIMARY KEY AUTO_INCREMENT,
  name  VARCHAR(200),
  valid VARCHAR(1),
  CHECK(valid='Y' OR valid='N')
);

CREATE TABLE ACTIVE_PRODUCTS(
  id                 INT PRIMARY KEY AUTO_INCREMENT,
  fk_product_type_id INT,
  active_since       DATE,
  FOREIGN KEY(fk_product_type_id) REFERENCES PRODUCT_TYPE(id)
);

CREATE TABLE USERS(
  id            INT PRIMARY KEY AUTO_INCREMENT,
  username      VARCHAR(100) NOT NULL,
  pasword       VARCHAR(500) NOT NULL,
  email         VARCHAR(100),
  fk_product_id INT,
  FOREIGN KEY(fk_product_id) REFERENCES ACTIVE_PRODUCTS(id)
);

/*ZATIAL NIE JE V DATABAZE*/
CREATE TABLE PRODUCTS_CONFIG(
  id                   INT PRIMARY KEY AUTO_INCREMENT,
  fk_active_product_id INT,
  /*TODO*/
  FOREIGN KEY(fk_active_product_id) REFERENCES ACTIVE_PRODUCTS(id)
);

CREATE TABLE COLLECTED_DATA(
  id            INT PRIMARY KEY AUTO_INCREMENT,
  fk_product_id INT,
  temperature   FLOAT(7,4) DEFAULT NULL,
  humidity      FLOAT(7,4) DEFAULT NULL,
  FOREIGN KEY(fk_product_id) REFERENCES ACTIVE_PRODUCTS(id)
);
