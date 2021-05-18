# springboot-jdbc-demo

Run sql script in sql server

CREATE DATABASE springboot_demo;
USE  springboot_demo;
CREATE TABLE customer (
  id              INT           NOT NULL    IDENTITY    PRIMARY KEY,
  first_name           VARCHAR(250)  NOT NULL,
  last_name  VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL
);
