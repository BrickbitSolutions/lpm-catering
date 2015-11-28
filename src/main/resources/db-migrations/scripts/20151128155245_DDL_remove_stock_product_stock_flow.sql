-- // DDL_remove_stock_product_stock_flow
-- Migration SQL that makes the change goes here.

alter table stock_flow drop FOREIGN KEY stock_flow_ibfk_1;
ALTER TABLE stock_flow DROP COLUMN STOCK_PRODUCT_ID;

-- //@UNDO
-- SQL to undo the change goes here.

ALTER TABLE stock_flow ADD (
  STOCK_PRODUCT_ID INTEGER(11) NOT NULL
);

ALTER TABLE stock_flow ADD FOREIGN KEY FK_STOCK_FLOW_ST_PROD(STOCK_PRODUCT_ID) REFERENCES stockproduct(ID);
