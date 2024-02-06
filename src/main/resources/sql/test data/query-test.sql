-- browsing all restaurants
explain analyze
select r.restaurant_id,r.created_by, r.creation_time,r.last_updated_time,
       r.updated_by,r.closing_time,r.opening_time,r.description,r.location,
       r.name,r.status
from restaurant r
where r.status = 'ACTIVE';

create index status_idx on restaurant (restaurant_id,status);
drop index if exists status_idx;
-- Browse All Menus Items
explain analyze
select
    m.menu_id ,m.created_by ,m.creation_time ,
    m.last_updated_time,m.updated_by,m.description,
    m.name,m.restaurant_id
from menu m where m.menu_id=1;
explain analyze
select
    item.menu_item_id,item.created_by ,item.creation_time ,
    item.last_updated_time,item.updated_by ,item.ingredients,item.menu_id,
    item.price,item.quantity , item.title
from menu_item item
    left outer join menu m on item.menu_id=m.menu_id where m.menu_id=1;

create index menu_id_idx on menu_item(menu_id,menu_item_id);
drop index if exists menu_id_idx;
-- Browse All Restaurant Menus
explain analyze
select
    menu0_.menu_id as menu_id1_5_,
    menu0_.created_by as created_2_5_,
    menu0_.creation_time as creation3_5_,
    menu0_.last_updated_time as last_upd4_5_,
    menu0_.updated_by as updated_5_5_,
    menu0_.description as descript6_5_,
    menu0_.name as name7_5_,
    menu0_.restaurant_id as restaura8_5_
from
    menu menu0_
        left outer join
    restaurant restaurant1_
    on menu0_.restaurant_id=restaurant1_.restaurant_id
where
    restaurant1_.restaurant_id=1;
create index rest_menu_idx on menu(restaurant_id,menu_id);
drop index if exists rest_menu_idx;
-- list all items in the cart
explain analyze
select
    cartitem0_.cart_item_id as cart_ite1_2_,
    cartitem0_.quantity as quantity2_2_,
    cartitem0_.total_price as total_pr3_2_,
    cartitem0_.unit_price as unit_pri4_2_
from
    cart cart0_
        right outer join cart_item cartitem0_ on cart0_.cart_id = cartitem0_.cart_id
where
    cartitem0_.cart_id=1;
create index ca_item_idx on cart_item(cart_id);
drop index if exists ca_item_idx;

-- search Menu Item By title
explain analyze
select
    menuitem0_.menu_item_id as menu_ite1_6_,
    menuitem0_.created_by as created_2_6_,
    menuitem0_.creation_time as creation3_6_,
    menuitem0_.last_updated_time as last_upd4_6_,
    menuitem0_.updated_by as updated_5_6_,
    menuitem0_.ingredients as ingredie6_6_,
    menuitem0_.menu_id as menu_id10_6_,
    menuitem0_.price as price7_6_,
    menuitem0_.quantity as quantity8_6_,
    menuitem0_.title as title9_6_
from
    menu_item menuitem0_
where
    menuitem0_.title like 'title';
create index title_idx on menu_item(title);