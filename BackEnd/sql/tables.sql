CREATE TABLE SMARTPOT.PRODUCT_TYPE{
  id    INT PRIMARY KEY AUTO_INCREMENT,
  name  VARCHAR2(200),
  valid VARCHAR2(1),
  CHECK (valid='Y' OR valid='N')
}

CREATE TABLE SMARTPOT.ACTIVE_PRODUCTS{
  product_id         INT PRIMARY KEY AUTO_INCREMENT,
  fk_product_type_id INT,
  active_since       DATE,
  /*CONFIG?*/
  FOREIGN KEY(fk_product_type_id) REFERENCES PRODUCT_TYPE(id)
}

CREATE TABLE SMARTPOT.COLLECTED_DATA{
  data_id       INT PRIMARY KEY AUTO_INCREMENT,
  fk_product_id INT,
  temperature   INT DEFAULT NULL,
  humidity      INT DEFAULT NULL,
  FOREIGN KEY(fk_product_id) REFERENCES ACTIVE_PRODUCTS(product_id)
}
