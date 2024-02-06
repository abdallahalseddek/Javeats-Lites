
INSERT INTO users(id,email, firstname, lastname, password, role)
SELECT a,'test@test.com' || a,'name' || a,
       'username' || a,'passwords' || a,'CUSTOMER'
FROM generate_series(2, 10000000) AS a;

INSERT INTO restaurant (restaurant_id,created_by,creation_time,last_updated_time, updated_by,closing_time,
                        opening_time,description,location,name,status)
SELECT
    i,'User' || i, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
    'User' || i, '18:00:00', '08:00:00', 'Description ' || i,
    'Location ' || i, 'Restaurant ' || i, 'ACTIVE'
FROM generate_series(1, 10000000) AS i;

INSERT INTO menu(menu_id,created_by, creation_time, last_updated_time,
                 updated_by, description, name, restaurant_id)
SELECT m,'User' || m, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
       'User' || m, 'menu', 'fries', 2
FROM generate_series(10000001, 130000) AS m;

INSERT INTO menu_item(menu_item_id,created_by, creation_time, last_updated_time,updated_by, ingredients, price, quantity,title, menu_id)
SELECT mi,'User' || mi, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
       'User' || mi, 'ingredients', 2.0 * mi , mi * random(), 'title',mi
FROM generate_series(1, 10000000) AS mi;

INSERT INTO customer(customer_id,created_by, creation_time, last_updated_time,
                     updated_by, user_id)
SELECT b,'User' || b,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'User' || b, b
FROM generate_series(1, 10000000) AS b;

INSERT INTO cart(cart_id,created_by, creation_time, last_updated_time,
                 updated_by, discount, cart_status, total_items, total_price, customer_id)
SELECT l,'User' || l, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
       'User' || l, 2.0*l, 'READ_WRITE',l, 5.0*l,l
FROM generate_series(1, 10000000) AS l;

INSERT INTO cart_item(cart_item_id,quantity, total_price, unit_price, cart_id)
SELECT ci,5 * ci, 3.0 * ci, 2.5* ci,1
FROM generate_series(10003001, 10010000) AS ci;

INSERT INTO address(address_id,contact_number, government, state, street, restaurant_id, customer_id)
SELECT ad,'contact' || ad, 'govern' || ad, 'state' || ad,
       'street' || ad, ad, ad
FROM generate_series(1, 10000000) AS ad;

INSERT INTO delivery (delivery_id,created_by,creation_time,last_updated_time,updated_by,estimated_time, delivery_status)
SELECT
    z,'User' || z,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,
    'User' || z,CURRENT_TIMESTAMP,'DELIVERED'
FROM generate_series(1, 10000000) AS z;


INSERT INTO orders(order_id,order_status,order_time,total_price,customer_id,restaurant_id)
SELECT c,'DELIVERED',CURRENT_TIMESTAMP,20.5 * c,c,c
FROM generate_series(1, 10000000) AS c;

INSERT INTO payment(payment_id,amount, payment_method, payment_status, order_id)
SELECT p,2.0 * p,'CASH','SUCCESS',p
FROM generate_series(1, 10000000) AS p;
