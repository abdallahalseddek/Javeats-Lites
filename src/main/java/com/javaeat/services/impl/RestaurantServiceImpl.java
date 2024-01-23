package com.javaeat.services.impl;

import com.javaeat.enums.ErrorMessage;
import com.javaeat.enums.Status;
import com.javaeat.exception.HandlerException;
import com.javaeat.exception.NotFoundException;
import com.javaeat.handler.order.OrderHandler;
import com.javaeat.model.Restaurant;
import com.javaeat.repository.RestaurantRepository;
import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import com.javaeat.request.RestaurantRequest;
import com.javaeat.services.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RestaurantServiceImpl extends OrderHandler implements RestaurantService {
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
        isRestaurantNotExists(restaurantRequest.getId());
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

    public void isRestaurantNotExists(Integer restaurantId) {
        if (restaurantRepository.findById(restaurantId).isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND.value(),
                    ErrorMessage.RESTAURANT_NOT_FOUND.name());
        }
    }

    @Override
    public OrderResponse handle(OrderRequest request, OrderResponse response) {

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(() -> new HandlerException("Restaurant with ID " + request.getRestaurantId() + " is not available."));

        // Mock restaurant with working hours from 10:00 AM to 8:00 PM
//        LocalTime openingTime = LocalTime.of(10, 0);
        LocalTime openingTime = restaurant.getOpeningTime();
//        LocalTime closingTime = LocalTime.of(23, 0);
        LocalTime closingTime = restaurant.getClosingTime();
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isBefore(openingTime) || currentTime.isAfter(closingTime)) {
            log.info("The restaurant is currently closed.");
            throw new HandlerException("The restaurant is currently closed.");
        }
        return handleNext(request,response);
    }
}
