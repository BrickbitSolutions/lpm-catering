-- // DDL_Alter_order_line_add_order_id
-- Migration SQL that makes the change goes here.

ALTER TABLE product_order_line ADD (
  ORDER_ID INTEGER(11) NOT NULL
);

ALTER TABLE product_order_line ADD FOREIGN KEY FK_ORDER_LINE_ORDER(ORDER_ID) REFERENCES product_order(ID);

-- //@UNDO
-- SQL to undo the change goes here.

ALTER TABLE product_order_line DROP COLUMN ORDER_ID;
