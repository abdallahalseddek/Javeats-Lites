package com.javaeat.services;

import com.javaeat.model.Restaurant;
import com.javaeat.request.RestaurantRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RestaurantService {
    Restaurant addNewRestaurant(RestaurantRequest restaurantRequest);

    Restaurant updateRestaurant(RestaurantRequest restaurantRequest);

    List<Restaurant> listAllRestaurants(Pageable pageable);

    Restaurant getRestaurantRating(Integer restaurantId);

    void deleteRestaurant(Integer restaurantId);



}
