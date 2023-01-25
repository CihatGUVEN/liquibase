-- liquibase formatted sql

-- changeset Cihat:1674638215421-1
CREATE TABLE country.cities (id BIGINT AUTO_INCREMENT NOT NULL, name VARCHAR(255) NULL, CONSTRAINT PK_CITIES PRIMARY KEY (id));

