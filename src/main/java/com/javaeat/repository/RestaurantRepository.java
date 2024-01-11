package com.javaeat.repository;

import com.javaeat.enums.Status;
import com.javaeat.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
    List<Restaurant> findAllByRestaurantStatus(Status status);
}
