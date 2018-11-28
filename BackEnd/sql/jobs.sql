CREATE EVENT j_rotate_tokens
    ON SCHEDULE
      EVERY 10 MINUTE
    DO
      BEGIN
        INSERT INTO INACTIVE_TOKENS(id, fk_product_id, salt, hash, token_created, status, token_swapped) 
            SELECT *,CURRENT_TIMESTAMP 
            FROM ACTIVE_TOKENS 
            WHERE status != 1;
        DELETE FROM ACTIVE_TOKENS WHERE status != 1;
      END