-- // DDL_Alter_Order
-- Migration SQL that makes the change goes here.

ALTER TABLE product_order ADD COLUMN (
  PLACED_BY_USER_ID INTEGER NOT NULL
);

-- //@UNDO
-- SQL to undo the change goes here.

ALTER TABLE PRODUCT_ORDER DROP COLUMN PLACED_BY_USER_ID;
