package com.javaeat.controller;


import com.javaeat.model.Menu;
import com.javaeat.request.RestaurantRequest;
import com.javaeat.response.RestaurantResponse;
import com.javaeat.services.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/restaurant")
@Slf4j
@Tag(name = "Restaurant Endpoints")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("/addRestaurant")
    @Operation(summary = "Add a new Restaurant", description = "Add a new Restaurant")
    public ResponseEntity<RestaurantResponse> addNewRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        return new ResponseEntity<>(restaurantService.addNewRestaurant(restaurantRequest),HttpStatus.CREATED);
    }
    @PatchMapping("/updateRestaurant")
    @Operation(summary = "update Restaurant info", description = "update Restaurant info")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        return new ResponseEntity<>(restaurantService.updateRestaurant(restaurantRequest),HttpStatus.ACCEPTED);
    }
    @GetMapping("/listRestaurantMenus/{restaurantId}")
    @Operation(summary = "Add a new Restaurant", description = "Add a new Restaurant")
    public ResponseEntity<List<Menu>> listAllRestaurantMenus(@PathVariable Integer restaurantId) {
        return new ResponseEntity<>(restaurantService.listAllRestaurantMenus(restaurantId),HttpStatus.FOUND);
    }
    @GetMapping("/getRating/{restaurantId}")
    @Operation(summary = "get the rating of Restaurant", description = "get the rating of Restaurant")
    public ResponseEntity<RestaurantResponse> getRestaurantRating(@PathVariable Integer restaurantId) {
        return new ResponseEntity<>(restaurantService.getRestaurantRating(restaurantId),HttpStatus.FOUND);
    }
    @DeleteMapping("/deleteRestaurant/{restaurantId}")
    @Operation(summary = "Add a new Restaurant", description = "Add a new Restaurant")
    public void deleteRestaurant(@PathVariable Integer restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
    }

}
