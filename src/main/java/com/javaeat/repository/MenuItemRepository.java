package com.javaeat.repository;

import com.javaeat.model.Menu;
import com.javaeat.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Integer> {
    void deleteMenuItemsByMenu(Menu menu);
//    @Query(value = "select item.menu_item_id,item.created_by ,item.creation_time ,item.last_updated_time,item.updated_by ,item.ingredients,item.menu_id, item.price,item.quantity , item.title from menu_item item  left outer join menu m on item.menu_id=m.menu_id where m.menu_id=:menuId ", nativeQuery = true)
    List<MenuItem> findAllByMenuId(
//            @Param("menuId")
            Integer menuId);

    List<MenuItem> findByTitleContaining(String title);

}
