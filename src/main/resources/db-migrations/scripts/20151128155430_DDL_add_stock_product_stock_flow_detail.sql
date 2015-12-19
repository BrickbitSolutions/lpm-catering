-- // DDL_add_stock_product_stock_flow_detail
-- Migration SQL that makes the change goes here.

ALTER TABLE stock_flow_detail ADD (
STOCK_PRODUCT_ID INTEGER(11) NOT NULL
);

ALTER TABLE stock_flow_detail ADD FOREIGN KEY FK_STOCK_FLOW_DETAIL_ST_PROD(STOCK_PRODUCT_ID) REFERENCES stockproduct(ID);

-- //@UNDO
-- SQL to undo the change goes here.

ALTER TABLE stock_flow_detail DROP FOREIGN KEY stock_flow_detail_ibfk_1;
ALTER TABLE stock_flow_detail DROP COLUMN STOCK_PRODUCT_ID;
