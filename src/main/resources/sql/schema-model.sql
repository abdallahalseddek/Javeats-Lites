
CREATE TABLE IF NOT EXISTS customer (
    customer_id SERIAL PRIMARY KEY,
    creation_time TIMESTAMP NOT NULL,
    last_updated_time TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS address (
  address_id SERIAL PRIMARY KEY,
  street VARCHAR(255),
    state VARCHAR(255),
    government VARCHAR(255),
    contact_number VARCHAR(255),
    customer_id INTEGER REFERENCES customer(customer_id)
    );
CREATE TABLE IF NOT EXISTS restaurant (
    restaurant_id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    location VARCHAR(255),
    status VARCHAR(255),
    opening_time TIME,
    closing_time TIME,
    creation_time TIMESTAMP NOT NULL,
    last_updated_time TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
    );
CREATE TABLE IF NOT EXISTS menu (
      menu_id SERIAL PRIMARY KEY,
     name VARCHAR(255),
    description VARCHAR(255),
    restaurant_id INTEGER REFERENCES restaurant(restaurant_id)  ,
    creation_time TIMESTAMP NOT NULL,
    last_updated_time TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS menu_item (
    menu_item_id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    ingredients VARCHAR(255),
    price DOUBLE PRECISION,
    quantity INTEGER,
    menu_id INTEGER REFERENCES menu(menu_id) ,
    creation_time TIMESTAMP NOT NULL,
    last_updated_time TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS cart (
 cart_id SERIAL PRIMARY KEY,
   total_price DOUBLE PRECISION,
    total_items INTEGER,
   cart_status VARCHAR(255),
    discount DOUBLE PRECISION,
    creation_time TIMESTAMP NOT NULL,
    last_updated_time TIMESTAMP NOT NULL,
    created_by VARCHAR(255),
    updated_by VARCHAR(255),
    customer_id INTEGER REFERENCES customer(customer_id)
    );

CREATE TABLE IF NOT EXISTS cart_item (
    cart_item_id SERIAL PRIMARY KEY,
    quantity INTEGER,
    unit_price DOUBLE PRECISION,
    total_price DOUBLE PRECISION,
    cart_id INTEGER REFERENCES cart(cart_id) ,
    menu_item_id INTEGER REFERENCES menu_item(menu_item_id)
    );





CREATE TABLE IF NOT EXISTS "order" (
    order_id SERIAL PRIMARY KEY,
    order_time TIMESTAMP NOT NULL,
    total_price DOUBLE PRECISION,
    order_status VARCHAR(255),
    customer_id INTEGER REFERENCES customer(customer_id) ,
    restaurant_id INTEGER REFERENCES restaurant(restaurant_id) ,
    UNIQUE (order_id)
    );

CREATE TABLE IF NOT EXISTS payment (
    payment_id SERIAL PRIMARY KEY,
    amount DOUBLE PRECISION,
    payment_method VARCHAR(255),
    payment_status VARCHAR(255),
    order_id INTEGER REFERENCES "order"(order_id)
    );


/
