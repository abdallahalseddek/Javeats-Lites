package com.javaeat.services;

import com.javaeat.model.Restaurant;
import com.javaeat.request.RestaurantRequest;

import java.util.List;

public interface RestaurantService {
    Restaurant addNewRestaurant(RestaurantRequest restaurantRequest);

    Restaurant updateRestaurant(RestaurantRequest restaurantRequest);

    List<Restaurant> listAllRestaurants();

    Restaurant getRestaurantRating(Integer restaurantId);

    void deleteRestaurant(Integer restaurantId);



}
