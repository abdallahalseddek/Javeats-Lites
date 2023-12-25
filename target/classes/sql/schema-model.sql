DROP DATABASE IF EXISTS javeats_lites;
CREATE DATABASE javaEats_lites;

-- create user table
DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS
(
    user_id   SERIAL PRIMARY KEY,
    firstname VARCHAR(50)  NOT NULL,
    lastname  VARCHAR(50),
    email     VARCHAR(50)  NOT NULL UNIQUE,
    password  VARCHAR(250) NOT NULL UNIQUE,
    user_type SERIAL REFERENCES userType (type_id)
);

-- create user type table
DROP TABLE IF EXISTS userType;
CREATE TABLE userType
(
    type_id SERIAL PRIMARY KEY,
    name    VARCHAR(255)
);

-- create customer table
DROP TABLE IF EXISTS customer;
CREATE TABLE customer
(
    customer_id SERIAL PRIMARY KEY,
    user_id     SERIAL REFERENCES USERS (user_id)
);

-- create address table
DROP TABLE IF EXISTS address;
CREATE TABLE address
(
    address_id   SERIAL PRIMARY KEY,
    street       VARCHAR(50),
    state        VARCHAR(50),
    government   VARCHAR(50),
    contact_numb VARCHAR(50),
    customer_id  SERIAL REFERENCES customer (customer_id)
);

-- create Role table
DROP TABLE IF EXISTS Role;
CREATE TABLE Role
(
    role_id SERIAL PRIMARY KEY,
    role    VARCHAR(50) NOT NULL
);

-- create userRole Table
DROP TABLE IF EXISTS UserRole;
CREATE TABLE UserRole
(
    user_id SERIAL REFERENCES USERS (user_id),
    role_id SERIAL REFERENCES Role (role_id),
    PRIMARY KEY (user_id, role_id)
);

-- create Restaurant Table
DROP TABLE IF EXISTS Restaurant;
CREATE TABLE Restaurant
(
    restaurant_id   SERIAL PRIMARY KEY,
    name            varchar(100) NOT NULL,
    contact_details VARCHAR(200) NOT NULL,
    location        VARCHAR(100) NOT NULL
);

-- create Restaurant Details Table
DROP TABLE IF EXISTS RestaurantDetails;
CREATE TABLE RestaurantDetails
(
    restaurant_details_id SERIAL PRIMARY KEY,
    restaurant_id         SERIAL REFERENCES Restaurant (restaurant_id)
);

-- create Menu Table
DROP TABLE IF EXISTS Menu;
CREATE TABLE Menu
(
    menu_id       SERIAL PRIMARY KEY,
    name          VARCHAR(100) NOT NULL,
    description   TEXT,
    restaurant_id SERIAL REFERENCES Restaurant (restaurant_id)
);

-- create Menu item Table
DROP TABLE IF EXISTS Menu_Item;
CREATE TABLE Menu_Item
(
    menu_item_id SERIAL PRIMARY KEY,
    title        VARCHAR(100) NOT NULL,
    ingredients  VARCHAR(100),
    price        DECIMAL(10, 2),
    menu_id      SERIAL REFERENCES Menu (menu_id)
);

-- create Cart Table
DROP TABLE IF EXISTS cart;
CREATE TABLE cart
(
    cart_id     SERIAL PRIMARY KEY,
    total_price DECIMAL(10, 2),
    session_id  SERIAL,
    created_at  TIMESTAMP,
    customer_id SERIAL REFERENCES customer (customer_id)
);

-- create cart item table
DROP TABLE IF EXISTS cart_item;
CREATE TABLE cart_item
(
    cart_item_id SERIAL PRIMARY KEY,
    menu_item_id SERIAL REFERENCES Menu_Item (menu_item_id),
    cart_id      SERIAL REFERENCES cart (cart_id)
);

-- create orders table
DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(
    order_id    SERIAL PRIMARY KEY,
    total_price DECIMAL(10, 2),
    created_at  TIMESTAMP,
    customer_id SERIAL REFERENCES customer (customer_id)
);

-- create order details table
DROP TABLE IF EXISTS order_details;
CREATE TABLE order_details
(
    orderD_id SERIAL PRIMARY KEY,
    status    VARCHAR(20),
    order_id  SERIAL REFERENCES orders (order_id),
    cart_id   SERIAL REFERENCES cart (cart_id)
);

DROP TABLE IF EXISTS order_tracking;
-- create address table
CREATE TABLE order_tracking
(
    id                 INT PRIMARY KEY,
    location           VARCHAR(255),
    estimate_date_time TIMESTAMP,
    order_id           SERIAL REFERENCES orders (order_id)
);

-- create auditing table
DROP TABLE IF EXISTS auditing;
CREATE TABLE auditing
(
    auditing_id SERIAL PRIMARY KEY,
    details     VARCHAR(225),
    type_id     INT,
    date        TIMESTAMP
);

-- create payment integration type table
DROP TABLE IF EXISTS payment_integration_type;
CREATE TABLE payment_integration_type
(
    id   INT PRIMARY KEY,
    name VARCHAR(255)
);

-- create payment status table
DROP TABLE IF EXISTS payment_status;
CREATE TABLE payment_status
(
    id   INT PRIMARY KEY,
    name VARCHAR(255)
);

-- create payment type configuration table
DROP TABLE IF EXISTS payment_type_configuration;
CREATE TABLE payment_type_configuration
(
    id                          INT PRIMARY KEY,
    configuration_details       VARCHAR(255),
    payment_integration_type_id SERIAL REFERENCES payment_integration_type (id)
);

-- create preferred payment settings table
DROP TABLE IF EXISTS preferred_payment_settings;
CREATE TABLE preferred_payment_settings
(
    id                     INT PRIMARY KEY,
    user_id                SERIAL REFERENCES USERS (user_id),
    payment_type_config_id SERIAL REFERENCES payment_type_configuration (id)
);

-- create transaction auditing table
DROP TABLE IF EXISTS transaction_auditing;
CREATE TABLE transaction_auditing
(
    transaction_id SERIAL REFERENCES transaction_table (id),
    auditing_id    SERIAL REFERENCES auditing (auditing_id),
    PRIMARY KEY (transaction_id, auditing_id)

);

-- create transaction details table
DROP TABLE IF EXISTS transaction_details;
CREATE TABLE transaction_details
(
    trans_id SERIAL PRIMARY KEY,
    details  VARCHAR(255)
);

-- create transaction payment status table
DROP TABLE IF EXISTS transaction_payment_status;
CREATE TABLE transaction_payment_status
(
    tran_pay_stat_id  SERIAL PRIMARY KEY,
    transaction_id    SERIAL REFERENCES transaction_table (id),
    payment_status_id SERIAL REFERENCES payment_status (id)
);

-- create transaction table table
DROP TABLE IF EXISTS transaction_table;
CREATE TABLE transaction_table
(
    id         SERIAL PRIMARY KEY,
    status     VARCHAR(255),
    audit_date DECIMAL,
    order_id   SERIAL REFERENCES orders (order_id)
);

-- create transaction transaction details table
DROP TABLE IF EXISTS transaction_transaction_details;
CREATE TABLE transaction_transaction_details
(
    trans_id   SERIAL REFERENCES transaction_table (id),
    details_id SERIAL REFERENCES transaction_details (trans_id),
    PRIMARY KEY (trans_id, details_id)

);

