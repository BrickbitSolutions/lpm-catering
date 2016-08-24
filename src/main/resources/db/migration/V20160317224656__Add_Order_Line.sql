CREATE TABLE PRODUCT_ORDER_LINE(
  ID BIGSERIAL,
  PRODUCT_ID BIGINT NOT NULL,
  QUANTITY INTEGER NOT NULL,
  ORDER_ID BIGINT NOT NULL,
  CONSTRAINT PK_PRODUCT_ORDER_LINE PRIMARY KEY (ID),
  CONSTRAINT FK_ORDER_LINE_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT (ID),
  CONSTRAINT FK_ORDER_LINE_ORDER FOREIGN KEY (ORDER_ID) REFERENCES product_order (ID)
);