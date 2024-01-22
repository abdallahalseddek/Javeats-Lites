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
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final LocalDateTime dateTime = LocalDateTime.now();

    @Override
    public Restaurant addNewRestaurant(RestaurantRequest restaurantRequest) {
        isRestaurantExists(restaurantRequest.getName());
        Restaurant restaurant = Restaurant.buildRestaurant(restaurantRequest);
        restaurant.setCreationTime(dateTime);
        // set opening and closing time
        restaurant.setOpeningTime(restaurantRequest.getOpeningTime());
        restaurant.setClosingTime(restaurantRequest.getClosingTime());
        return restaurantRepository.save(restaurant);
    }

    @Override
    @Transactional
    public Restaurant updateRestaurant(RestaurantRequest restaurantRequest) {
        isRestaurantNotExists(restaurantRequest.getName());
        Restaurant restaurant = Restaurant.buildRestaurant(restaurantRequest);
        restaurant.setLastUpdatedTime(dateTime);
        restaurant.setOpeningTime(restaurantRequest.getOpeningTime());
        restaurant.setClosingTime(restaurantRequest.getClosingTime());
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

    public void isRestaurantExists(String restaurantName ) {
        if (restaurantRepository.findByName(restaurantName).isPresent()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(),
                    ErrorMessage.RESTAURANT_ALREADY_EXISTS.name());
        }
    }

    public void isRestaurantNotExists(String restaurantName) {
        if (restaurantRepository.findByName(restaurantName).isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(),
                    ErrorMessage.RESTAURANT_NOT_FOUND.name());
        }
    }
}
