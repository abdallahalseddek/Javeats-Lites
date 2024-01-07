package com.javaeat.repository;

import com.javaeat.model.Restaurant;
import com.javaeat.model.RestaurantDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RestaurantDetailsRepositoryTest {

    @Autowired
    private RestaurantDetailsRepository detailsRepository;
    private RestaurantDetails restaurantDetails;
    private RestaurantDetails saved;
    @BeforeEach
    void setUp() {
        restaurantDetails = RestaurantDetails.builder()
                .restaurant(new Restaurant())
                .name("Food Free")
                .contactDetails("contact")
                .build();
        saved = detailsRepository.save(restaurantDetails);
    }

    @Test
    void should_saved_RestaurantDetails_return_SavedRestaurantDetails() {

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isGreaterThan(0);
    }

    @Test
    void should_update_RestaurantDetails_return_updatedRestaurantDetails() {
        saved.setLocation("new Location update");
        saved.setDescription("Eat For Free");
        RestaurantDetails response = detailsRepository.save(saved);
        assertThat(response).isNotNull();
        assertThat(response.getLocation()).isNotNull();
        assertThat(response.getDescription()).isNotNull();
        assertEquals(response.getId(),restaurantDetails.getId());
    }

    @Test
    void should_delete_RestaurantDetails() {
        detailsRepository.delete(saved);
        Optional<RestaurantDetails> response = detailsRepository.findById(restaurantDetails.getId());

        assertThat(response).isEmpty();
    }

    @Test
    void should_get_RestaurantDetails_ById() {
        Optional<RestaurantDetails> response = detailsRepository.findById(restaurantDetails.getId());
        assertThat(response).isNotNull();
        assertEquals(response.get().getId(),restaurantDetails.getId());
        assertEquals(response.get().getName(),restaurantDetails.getName());
        assertEquals(response.get().getLocation(),restaurantDetails.getLocation());
    }
}