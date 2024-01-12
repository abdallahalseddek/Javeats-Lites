package com.javaeat.repository;

import com.javaeat.model.Menu;
import com.javaeat.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Integer> {
    void deleteMenuItemsByMenu(Menu menu);
    List<MenuItem> findAllByMenuId(Integer menuId);

    List<MenuItem> findByTitleContaining(String title);

}
