-- Create userType table
DROP TABLE IF EXISTS userType;
CREATE TABLE userType (
                          type_id SERIAL PRIMARY KEY,
                          name VARCHAR(255)
);

-- Create Role table
DROP TABLE IF EXISTS Role;
CREATE TABLE Role (
                      role_id SERIAL PRIMARY KEY,
                      role VARCHAR(50) NOT NULL
);

-- Create USERS table
DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS (
                       user_id SERIAL PRIMARY KEY,
                       firstname VARCHAR(50) NOT NULL,
                       lastname VARCHAR(50),
                       email VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(250) NOT NULL UNIQUE,
                       user_type INT REFERENCES userType (type_id)
);

-- Create UserRole Table
DROP TABLE IF EXISTS UserRole;
CREATE TABLE UserRole (
                          user_id INT REFERENCES USERS (user_id),
                          role_id INT REFERENCES Role (role_id),
                          PRIMARY KEY (user_id, role_id)
);

-- Create customer table
DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
                          customer_id SERIAL PRIMARY KEY,
                          user_id INT REFERENCES USERS (user_id)
);

-- Create address table
DROP TABLE IF EXISTS address;
CREATE TABLE address (
                         address_id SERIAL PRIMARY KEY,
                         street VARCHAR(50),
                         state VARCHAR(50),
                         government VARCHAR(50),
                         contact_numb VARCHAR(50),
                         customer_id INT REFERENCES customer (customer_id)
);

-- Create order status table
DROP TABLE IF EXISTS order_status;
CREATE TABLE order_status (
                              id INT PRIMARY KEY,
                              name VARCHAR(255)
);

-- Create payment integration type table
DROP TABLE IF EXISTS payment_integration_type;
CREATE TABLE payment_integration_type (
                                          id INT PRIMARY KEY,
                                          name VARCHAR(255)
);

-- Create payment status table
DROP TABLE IF EXISTS payment_status;
CREATE TABLE payment_status (
                                id INT PRIMARY KEY,
                                name VARCHAR(255)
);

-- Create Restaurant Table
DROP TABLE IF EXISTS Restaurant;
CREATE TABLE Restaurant (
                            restaurant_id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            contact_details VARCHAR(200) NOT NULL,
                            location VARCHAR(100) NOT NULL
);

-- Create Restaurant Details Table
DROP TABLE IF EXISTS RestaurantDetails;
CREATE TABLE RestaurantDetails (
                                   restaurant_details_id SERIAL PRIMARY KEY,
                                   restaurant_id INT REFERENCES Restaurant (restaurant_id)
);

-- Create Menu Table
DROP TABLE IF EXISTS Menu;
CREATE TABLE Menu (
                      menu_id SERIAL PRIMARY KEY,
                      name VARCHAR(100) NOT NULL,
                      description TEXT,
                      restaurant_id INT REFERENCES Restaurant (restaurant_id)
);

-- Create Menu Item Table
DROP TABLE IF EXISTS Menu_Item;
CREATE TABLE Menu_Item (
                           menu_item_id SERIAL PRIMARY KEY,
                           title VARCHAR(100) NOT NULL,
                           ingredients VARCHAR(100),
                           price DECIMAL(10, 2),
                           menu_id INT REFERENCES Menu (menu_id)
);

-- Create cart table
DROP TABLE IF EXISTS cart;
CREATE TABLE cart (
                      cart_id SERIAL PRIMARY KEY,
                      total_price DECIMAL(10, 2),
                      session_id INT,
                      created_at TIMESTAMP,
                      customer_id INT REFERENCES customer (customer_id)
);

-- Create cart item table
DROP TABLE IF EXISTS cart_item;
CREATE TABLE cart_item (
                           cart_item_id SERIAL PRIMARY KEY,
                           menu_item_id INT REFERENCES Menu_Item (menu_item_id),
                           cart_id INT REFERENCES cart (cart_id)
);

-- Create delivery status table
DROP TABLE IF EXISTS delivery_status;
CREATE TABLE delivery_status (
                                 id INT PRIMARY KEY,
                                 name VARCHAR(255)
);

-- Create DELIVERY table
DROP TABLE IF EXISTS DELIVERY;
CREATE TABLE DELIVERY (
                          delivery_id SERIAL PRIMARY KEY,
                          estimated_time TIMESTAMP NOT NULL,
                          delivery_status INT REFERENCES delivery_status (id),
                          order_id INT -- This will be defined later as a foreign key due to dependency
);

-- Create PAYMENT table
DROP TABLE IF EXISTS PAYMENT;
CREATE TABLE PAYMENT (
                         payment_id SERIAL PRIMARY KEY,
                         amount DECIMAL(5, 3) NOT NULL,
                         payment_method_id INT REFERENCES payment_integration_type (id),
                         payment_status_id INT REFERENCES payment_status (id),
                         order_id INT -- This will be defined later as a foreign key due to dependency
);

-- Create ORDERS table
DROP TABLE IF EXISTS ORDERS;
CREATE TABLE ORDERS (
                        order_id SERIAL PRIMARY KEY,
                        order_time TIMESTAMP NOT NULL,
                        total_price DECIMAL(5, 3) NOT NULL,
                        order_status_id INT REFERENCES order_status (id),
                        payment_id INT REFERENCES PAYMENT (payment_id),
                        delivery_id INT REFERENCES DELIVERY (delivery_id),
                        restaurant_id INT REFERENCES Restaurant (restaurant_id),
                        customer_id INT REFERENCES customer (customer_id)
);

-- Now that ORDERS table is created, we can define foreign keys in DELIVERY and PAYMENT tables
ALTER TABLE DELIVERY ADD FOREIGN KEY (order_id) REFERENCES ORDERS (order_id);
ALTER TABLE PAYMENT ADD FOREIGN KEY (order_id) REFERENCES ORDERS (order_id);

-- Create order details table
DROP TABLE IF EXISTS order_details;
CREATE TABLE order_details (
                               orderD_id SERIAL PRIMARY KEY,
                               status VARCHAR(20),
                               order_id INT REFERENCES ORDERS (order_id),
                               cart_id INT REFERENCES cart (cart_id)
);

-- Create order tracking table
DROP TABLE IF EXISTS order_tracking;
CREATE TABLE order_tracking (
                                id INT PRIMARY KEY,
                                location VARCHAR(255),
                                estimate_date_time TIMESTAMP,
                                order_id INT REFERENCES ORDERS (order_id)
);

-- Create auditing table
DROP TABLE IF EXISTS auditing;
CREATE TABLE auditing (
                          auditing_id SERIAL PRIMARY KEY,
                          details VARCHAR(225),
                          type_id INT,
                          date TIMESTAMP
);

-- Create payment type configuration table
DROP TABLE IF EXISTS payment_type_configuration;
CREATE TABLE payment_type_configuration (
                                            id INT PRIMARY KEY,
                                            configuration_details VARCHAR(255),
                                            payment_integration_type_id INT REFERENCES payment_integration_type (id)
);

-- Create preferred payment settings table
DROP TABLE IF EXISTS preferred_payment_settings;
CREATE TABLE preferred_payment_settings (
                                            id INT PRIMARY KEY,
                                            user_id INT REFERENCES USERS (user_id),
                                            payment_type_config_id INT REFERENCES payment_type_configuration (id)
);

-- Create transaction table
DROP TABLE IF EXISTS transaction_table;
CREATE TABLE transaction_table (
                                   id SERIAL PRIMARY KEY,
                                   status VARCHAR(255),
                                   audit_date TIMESTAMP,
                                   order_id INT REFERENCES ORDERS (order_id)
);

-- Create transaction details table
DROP TABLE IF EXISTS transaction_details;
CREATE TABLE transaction_details (
                                     trans_id SERIAL PRIMARY KEY,
                                     details VARCHAR(255)
);

-- Create transaction payment status table
DROP TABLE IF EXISTS transaction_payment_status;
CREATE TABLE transaction_payment_status (
                                            tran_pay_stat_id SERIAL PRIMARY KEY,
                                            transaction_id INT REFERENCES transaction_table (id),
                                            payment_status_id INT REFERENCES payment_status (id)
);

-- Create transaction auditing table
DROP TABLE IF EXISTS transaction_auditing;
CREATE TABLE transaction_auditing (
                                      transaction_id INT REFERENCES transaction_table (id),
                                      auditing_id INT REFERENCES auditing (auditing_id),
                                      PRIMARY KEY (transaction_id, auditing_id)
);

-- Create transaction transaction details table
DROP TABLE IF EXISTS transaction_transaction_details;
CREATE TABLE transaction_transaction_details (
                                                 trans_id INT REFERENCES transaction_table (id),
                                                 details_id INT REFERENCES transaction_details (trans_id),
                                                 PRIMARY KEY (trans_id, details_id)
);
/