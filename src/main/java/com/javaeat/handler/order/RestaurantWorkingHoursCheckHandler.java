package com.javaeat.handler.order;

import com.javaeat.model.Restaurant;
import com.javaeat.repository.RestaurantRepository;
import com.javaeat.request.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

@Builder
@Slf4j
@AllArgsConstructor
public class RestaurantWorkingHoursCheckHandler extends OrderHandler {

    private final RestaurantRepository restaurantRepository;
    @Override
    public boolean handle(OrderRequest request) {

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow();

        // Mock restaurant with working hours from 10:00 AM to 8:00 PM
        LocalTime openingTime = LocalTime.of(10, 0);
//        LocalTime openingTime = restaurant.getOpeningTime();
        LocalTime closingTime = LocalTime.of(20, 0);
//        LocalTime closingTime = restaurant.getClosingTime();
        LocalTime currentTime = LocalTime.now();

        if (currentTime.isBefore(openingTime) || currentTime.isAfter(closingTime)) {
            log.info("The restaurant is currently closed.");
            return false;
        }
        return handleNext(request);

    }
}