-- // DDL_Add_Status_To_Product_order
-- Migration SQL that makes the change goes here.

ALTER TABLE product_order ADD(
  STATUS VARCHAR(15) NOT NULL
);

-- //@UNDO
-- SQL to undo the change goes here.

ALTER TABLE product_order DROP COLUMN STATUS;
