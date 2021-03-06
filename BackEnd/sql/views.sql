CREATE OR REPLACE VIEW KEY_AND_TOKEN AS
  SELECT AP.id, AP.security_key, ATOK.salt
  FROM ACTIVE_PRODUCTS AP
  JOIN ACTIVE_TOKENS ATOK
  ON ATOK.fk_product_id = AP.id;