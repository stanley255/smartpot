CREATE TABLE ACTIVE_PRODUCTS(
  id                 INT PRIMARY KEY AUTO_INCREMENT,
  active_since       DATETIME
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
  rep_date      DATETIME,
  temperature   FLOAT(7,4) DEFAULT NULL,
  humidity      FLOAT(7,4) DEFAULT NULL,
  FOREIGN KEY(fk_product_id) REFERENCES ACTIVE_PRODUCTS(id)
);

CREATE TABLE ACTIVE_TOKENS(
  id              INT PRIMARY KEY AUTO_INCREMENT,
  fk_product_id   INT,
  salt            VARCHAR(200),
  token_created   DATETIME,
  status          INT,
  FOREIGN KEY(fk_product_id) REFERENCES ACTIVE_PRODUCTS(id)
);