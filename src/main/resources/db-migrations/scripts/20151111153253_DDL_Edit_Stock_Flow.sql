-- // DDL_Edit_Stock_Flow
-- Migration SQL that makes the change goes here.

ALTER TABLE stock_flow DROP QUANTITY;
ALTER TABLE stock_flow DROP PRICE_PER_UNIT;

-- //@UNDO
-- SQL to undo the change goes here.

ALTER TABLE stock_flow ADD(
  QUANTITY INTEGER NOT NULL,
  PRICE_PER_UNIT DOUBLE NOT NULL
);
