CREATE TABLE PREPARATION(
  ID BIGSERIAL,
  QUEUE_NAME VARCHAR(30) NOT NULL,
  TIMER INTEGER NOT NULL,
  INSTRUCTIONS VARCHAR(250),
  PRODUCT_ID BIGINT NOT NULL,
  CONSTRAINT PK_PREPARATION PRIMARY KEY (ID),
  CONSTRAINT FK_PREPARATION_PRODUCT FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(ID)
);