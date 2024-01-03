package com.javaeat.services.impl;

import com.javaeat.model.Menu;
import com.javaeat.repository.RestaurantRepository;
import com.javaeat.request.RestaurantRequest;
import com.javaeat.response.RestaurantResponse;
import com.javaeat.services.RestaurantService;
import com.javaeat.util.GenericMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final GenericMapper mapper;
    @Override
    public RestaurantResponse addNewRestaurant(RestaurantRequest restaurantRequest) {
        return null;
    }

    @Override
    public RestaurantResponse updateRestaurant(RestaurantRequest restaurantRequest) {
        return null;
    }

    @Override
    public List<Menu> listAllRestaurantMenus(Integer restaurantId) {
        return null;
    }

    @Override
    public RestaurantResponse getRestaurantRating(Integer restaurantId) {
        return null;
    }

    @Override
    public void deleteRestaurant(Integer restaurantId) {

    }
}
