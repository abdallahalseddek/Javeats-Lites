package com.javaeat.repository;

import com.javaeat.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer> {
    List<Menu> findByRestaurantId(Integer restaurantId);
}
