package com.javaeat.services.impl;

import com.javaeat.enums.ErrorMessage;
import com.javaeat.enums.Status;
import com.javaeat.exception.NotFoundException;
import com.javaeat.model.Restaurant;
import com.javaeat.repository.RestaurantRepository;
import com.javaeat.request.RestaurantRequest;
import com.javaeat.services.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final LocalDateTime dateTime = LocalDateTime.now();

    @Override
    public Restaurant addNewRestaurant(RestaurantRequest restaurantRequest) {
        isRestaurantExists(restaurantRequest.getId());
        Restaurant restaurant = Restaurant.buildRestaurant(restaurantRequest);
        restaurant.setCreationTime(dateTime);
        return restaurantRepository.save(restaurant);
    }

    @Override
    @Transactional
    public Restaurant updateRestaurant(RestaurantRequest restaurantRequest) {
        isRestaurantNotExists(restaurantRequest.getId());
        Restaurant restaurant = Restaurant.buildRestaurant(restaurantRequest);
        restaurant.setLastUpdatedTime(dateTime);
        return restaurantRepository.save(restaurant);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Integer restaurantId) {
        isRestaurantNotExists(restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        restaurant.setRestaurantStatus(Status.DELETED);
        restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> listAllRestaurants() {
        return restaurantRepository.findAllByRestaurantStatus(Status.ACTIVE);
    }

    @Override
    public Restaurant getRestaurantRating(Integer restaurantId) {
        return null;
    }

    public void isRestaurantExists(Integer restaurantId) {
        if (restaurantRepository.findById(restaurantId).isPresent()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(),
                    ErrorMessage.RESTAURANT_ALREADY_EXISTS.name());
        }
    }

    public void isRestaurantNotExists(Integer restaurantId) {
        if (restaurantRepository.findById(restaurantId).isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(),
                    ErrorMessage.RESTAURANT_NOT_FOUND.name());
        }
    }
}
