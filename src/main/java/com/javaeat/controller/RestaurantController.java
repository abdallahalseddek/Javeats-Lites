package com.javaeat.controller;


import com.javaeat.request.RestaurantRequest;
import com.javaeat.services.RestaurantService;
import com.javaeat.util.ApiResponse1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("api/restaurant")
@Slf4j
@Tag(name = "Restaurant Endpoints")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;

    @PostMapping("/addRestaurant")
    @Operation(summary = "Add a new Restaurant", description = "Add a new Restaurant")
    public ResponseEntity<ApiResponse1> addNewRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        return ApiResponse1.createUnifiedResponse(
                        restaurantService.addNewRestaurant(restaurantRequest),
                        HttpStatus.CREATED, "Successful operation", Arrays.asList("Bad request. Invalid input data",
                                "Restaurant not found or Item not Found",
                                "Internal server error. Something went wrong"));
    }
    @PatchMapping("/updateRestaurant")
    @Operation(summary = "update Restaurant info", description = "update Restaurant info")
    public ResponseEntity<ApiResponse1> updateRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
        return ApiResponse1.createUnifiedResponse(
                restaurantService.updateRestaurant(restaurantRequest),
                HttpStatus.CREATED, "Successful operation", Arrays.asList("Bad request. Invalid input data",
                        "Restaurant not found or Item not Found",
                        "Internal server error. Something went wrong"));
    }
    @GetMapping("/listRestaurantMenus/{restaurantId}")
    @Operation(summary = "Add a new Restaurant", description = "Add a new Restaurant")
    public ResponseEntity<ApiResponse1> listAllRestaurantMenus(@PathVariable Integer restaurantId) {
        return ApiResponse1.createUnifiedResponse(
                restaurantService.listAllRestaurantMenus(restaurantId),
                HttpStatus.CREATED, "Successful operation", Arrays.asList("Bad request. Invalid input data",
                        "Restaurant not found or Item not Found",
                        "Internal server error. Something went wrong"));
    }
    @GetMapping("/getRating/{restaurantId}")
    @Operation(summary = "get the rating of Restaurant", description = "get the rating of Restaurant")
    public ResponseEntity<ApiResponse1> getRestaurantRating(@PathVariable Integer restaurantId) {
        return ApiResponse1.createUnifiedResponse(
                restaurantService.getRestaurantRating(restaurantId),
                HttpStatus.CREATED, "Successful operation", Arrays.asList("Bad request. Invalid input data",
                        "Restaurant not found or Item not Found",
                        "Internal server error. Something went wrong"));
    }
    @DeleteMapping("/deleteRestaurant/{restaurantId}")
    @Operation(summary = "Add a new Restaurant", description = "Add a new Restaurant")
    public void deleteRestaurant(@PathVariable Integer restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
    }

}
