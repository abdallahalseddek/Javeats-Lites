package com.javaeat.services.impl;

import com.javaeat.enums.ErrorMessage;
import com.javaeat.exception.NotFoundException;
import com.javaeat.model.Menu;
import com.javaeat.model.Restaurant;
import com.javaeat.model.RestaurantDetails;
import com.javaeat.repository.MenuRepository;
import com.javaeat.repository.RestaurantDetailsRepository;
import com.javaeat.repository.RestaurantRepository;
import com.javaeat.request.RestaurantRequest;
import com.javaeat.response.RestaurantResponse;
import com.javaeat.services.RestaurantService;
import com.javaeat.util.GenericMapper;
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
    private final RestaurantDetailsRepository detailsRepository;
    private final MenuRepository menuRepository;
    private final GenericMapper mapper;

    @Override
    public RestaurantResponse addNewRestaurant(RestaurantRequest restaurantRequest) {
        if (restaurantRepository.findById(restaurantRequest.getId()).isPresent()) {
            throw new NotFoundException(HttpStatus.FORBIDDEN.value(),
                    ErrorMessage.RESTAURANT_ALREADY_EXISTS.name());
        }
        Restaurant restaurant = mapper.convert(restaurantRequest, Restaurant.class);
        RestaurantDetails restaurantDetails = setRestaurantDetails(restaurant, restaurantRequest);
        detailsRepository.save(restaurantDetails);
        return mapper.convert(restaurantDetails, RestaurantResponse.class);
    }

    @Override
    @Transactional
    public RestaurantResponse updateRestaurant(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = checkRestaurantExists(restaurantRequest.getId());
        RestaurantDetails restaurantDetails = setRestaurantDetails(restaurant, restaurantRequest);
        detailsRepository.save(restaurantDetails);
        return mapper.convert(restaurantDetails, RestaurantResponse.class);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Integer restaurantId) {
        Restaurant restaurant = checkRestaurantExists(restaurantId);
        detailsRepository.delete(restaurant.getRestaurantDetails());
    }

    @Override
    public List<Menu> listAllRestaurantMenus(Integer restaurantId) {
        Restaurant restaurant = checkRestaurantExists(restaurantId);
//        return restaurant.getMenus();
        return menuRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public RestaurantResponse getRestaurantRating(Integer restaurantId) {
        return null;
    }

    @Override
    public Restaurant checkRestaurantExists(Integer RestaurantId) {
        return restaurantRepository.findById(RestaurantId)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(),
                        ErrorMessage.RESTAURANT_NOT_FOUND.name()));
    }

    private RestaurantDetails setRestaurantDetails(Restaurant restaurant, RestaurantRequest restaurantRequest) {
        RestaurantDetails restaurantDetails = new RestaurantDetails(
                restaurantRequest.getId(),
                restaurantRequest.getName(),
                restaurantRequest.getDescription(),
                restaurantRequest.getContactDetails(),
                restaurantRequest.getLocation(),
                restaurant);
        restaurant.setRestaurantDetails(restaurantDetails);
        restaurant.setCreationTime(LocalDateTime.now());
        restaurant.setLastUpdatedTime(LocalDateTime.now());
        restaurant.setCreatedBy(restaurantRequest.getCreatedBy());
        restaurant.setUpdatedBy(restaurantRequest.getUpdatedBy());
        return restaurantDetails;
    }
}
