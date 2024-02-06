package com.javaeat.repository;

import com.javaeat.enums.Status;
import com.javaeat.model.Restaurant;
import com.javaeat.response.RestaurantResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {
    List<Restaurant> findAllByRestaurantStatus(Status status, Pageable pageable);

    Optional<Restaurant> findByName(String name);
}
