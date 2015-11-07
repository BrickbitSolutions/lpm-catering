-- // DDL_Remove_Total_Price_From_Order
-- Migration SQL that makes the change goes here.

ALTER TABLE product_order DROP COLUMN TOTAL_PRICE;

-- //@UNDO
-- SQL to undo the change goes here.

ALTER TABLE product_order ADD (
  TOTAL_PRICE DOUBLE NOT NULL
);
