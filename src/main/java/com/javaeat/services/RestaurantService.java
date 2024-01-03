package com.javaeat.services;

import com.javaeat.model.Menu;
import com.javaeat.request.RestaurantRequest;
import com.javaeat.response.RestaurantResponse;

import java.util.List;

public interface RestaurantService {
    RestaurantResponse addNewRestaurant(RestaurantRequest restaurantRequest);

    RestaurantResponse updateRestaurant(RestaurantRequest restaurantRequest);

    List<Menu> listAllRestaurantMenus(Integer restaurantId);

    RestaurantResponse getRestaurantRating(Integer restaurantId);

    void deleteRestaurant(Integer restaurantId);
}
