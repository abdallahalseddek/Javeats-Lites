package com.javaeat.repository;

import com.javaeat.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RestaurantRepositoryTest {
    @Autowired
    private RestaurantRepository restaurantRepository;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = Restaurant.builder()
                .restaurantDetails(new RestaurantDetails()).build();
    }

    @Test
    void should_saved_Restaurant_return_SavedRestaurant() {
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        assertThat(savedRestaurant).isNotNull();
        assertThat(savedRestaurant.getId()).isGreaterThan(0);
    }

    @Test
    void should_update_Restaurant_return_updatedRestaurant() {
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        savedRestaurant.setUpdatedBy("ahmed update test");
        savedRestaurant.setLastUpdatedTime(LocalDateTime.now().minusDays(5));
        Restaurant response = restaurantRepository.save(savedRestaurant);

        assertThat(response.getUpdatedBy()).isNotNull();
        assertThat(response.getLastUpdatedTime()).isNotNull();
    }

    @Test
    void should_delete_Restaurant() {
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        restaurantRepository.delete(savedRestaurant);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurant.getId());
        assertThat(optionalRestaurant).isEmpty();
    }

    @Test
    void should_get_Restaurant_ById() {
        restaurantRepository.save(restaurant);
        Restaurant response = restaurantRepository.findById(restaurant.getId()).get();

        assertThat(response).isNotNull();
        assertThat(response.getId()).isGreaterThan(0);
        assertEquals(response.getId(),restaurant.getId());
    }
}