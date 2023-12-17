CREATE DATABASE PCStore;

USE PCStore;

CREATE TABLE IF NOT EXISTS Family (
    id INT AUTO_INCREMENT PRIMARY KEY,
    typeFamily VARCHAR(255),
    sticks INT,
    battery INT,
    weight VARCHAR(255),
    storage VARCHAR(255),
    gpu VARCHAR(255),
    ramList VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Product (
    code INT PRIMARY KEY,
    name VARCHAR(255),
    price FLOAT,
    productType VARCHAR(255),
    rating FLOAT
);


CREATE TABLE IF NOT EXISTS Ram (
    code INT PRIMARY KEY,
    name VARCHAR(255),
    price FLOAT,
    GBs INT,
    productType VARCHAR(255),
    rating FLOAT
);


CREATE TABLE IF NOT EXISTS Storage (
    code INT PRIMARY KEY,
    name VARCHAR(255),
    price FLOAT,
    productType VARCHAR(255),
    rating FLOAT,
    TypeStorage VARCHAR(255),
    StorageSpace INT
);

CREATE TABLE IF NOT EXISTS Family_Product (
    family_id INT,
    product_id INT,
    FOREIGN KEY (family_id) REFERENCES Family(id),
    FOREIGN KEY (product_id) REFERENCES Product(code)
);

CREATE TABLE IF NOT EXISTS Family_Ram (
    family_id INT,
    ram_id INT,
    FOREIGN KEY (family_id) REFERENCES Family(id),
    FOREIGN KEY (ram_id) REFERENCES Ram(code)
);

CREATE TABLE IF NOT EXISTS Family_Storage (
    family_id INT,
    storage_id INT,
    FOREIGN KEY (family_id) REFERENCES Family(id),
    FOREIGN KEY (storage_id) REFERENCES Storage(code)
);

CREATE TABLE ExpecificFamily (
    id INT AUTO_INCREMENT PRIMARY KEY,
    typeFamily VARCHAR(255),
    sticks INT,
    battery INT,
    weight VARCHAR(255),
    ramList VARCHAR(1000),
    storage VARCHAR(255),
    gpu VARCHAR(255),
    expecificFamily VARCHAR(255)
);

CREATE TABLE ExpecificFamily_Product (
    expecificFamily_id INT,
    product_id INT,
    FOREIGN KEY (expecificFamily_id) REFERENCES ExpecificFamily(id),
    FOREIGN KEY (product_id) REFERENCES Product(code),
    PRIMARY KEY (expecificFamily_id, product_id)
);

CREATE TABLE ExpecificFamily_Ram (
    expecificFamily_id INT,
    ram_id INT,
    FOREIGN KEY (expecificFamily_id) REFERENCES ExpecificFamily(id),
    FOREIGN KEY (ram_id) REFERENCES Ram(code),
    PRIMARY KEY (expecificFamily_id, ram_id)
);

CREATE TABLE ExpecificFamily_Storage (
    expecificFamily_id INT,
    storage_id INT,
    FOREIGN KEY (expecificFamily_id) REFERENCES ExpecificFamily(id),
    FOREIGN KEY (storage_id) REFERENCES Storage(code),
    PRIMARY KEY (expecificFamily_id, storage_id)
);

CREATE TABLE administrators (
    ID INT PRIMARY KEY,
    name VARCHAR(255),
    lastName VARCHAR(255),
    username VARCHAR(255) UNIQUE,
    password VARCHAR(255)
);

CREATE TABLE clients (
    ID INT PRIMARY KEY,
    name VARCHAR(255),
    lastName VARCHAR(255),
    email VARCHAR(255),
    phoneNumber VARCHAR(20),
    username VARCHAR(50) UNIQUE,
    password VARCHAR(50)
);

CREATE TABLE bills (
    billNumber INT PRIMARY KEY,
    date DATE,
    name VARCHAR(50),
    lastName VARCHAR(50),
    phoneNumber VARCHAR(20),
    ram VARCHAR(50),
    processor VARCHAR(50),
    storage VARCHAR(50),
    powerSupply VARCHAR(50),
    motherboard VARCHAR(50),
    videoCard VARCHAR(50),
    price FLOAT
);
