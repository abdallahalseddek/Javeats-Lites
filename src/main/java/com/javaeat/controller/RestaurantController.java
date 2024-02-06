package com.javaeat.controller;


import com.javaeat.request.RestaurantRequest;
import com.javaeat.response.RestaurantResponse;
import com.javaeat.services.RestaurantService;
import com.javaeat.util.MapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/restaurant")
@Slf4j
@Tag(name = "Restaurant Endpoints")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final MapperUtil mapper;

    @PostMapping("/create")
    @Operation(summary = "Add a new Restaurant", description = "Add a new Restaurant")
    public ResponseEntity<RestaurantResponse> addNewRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        RestaurantResponse response = mapper.mapEntity(restaurantService.addNewRestaurant(restaurantRequest), RestaurantResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/update")
    @Operation(summary = "update Restaurant info", description = "update Restaurant info")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        RestaurantResponse response = mapper.mapEntity(restaurantService.updateRestaurant(restaurantRequest), RestaurantResponse.class);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/rating/{restaurantId}")
    @Operation(summary = "get the rating of Restaurant", description = "get the rating of Restaurant")
    public ResponseEntity<RestaurantResponse> getRestaurantRating(@PathVariable Integer restaurantId) {
        RestaurantResponse response = mapper.mapEntity(restaurantService.getRestaurantRating(restaurantId), RestaurantResponse.class);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @GetMapping("/all")
    @Operation(summary = "list all Restaurants", description = "list all Restaurants")
    public ResponseEntity<List<RestaurantResponse>> listAllRestaurants(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page,size);
        List<RestaurantResponse> responseList = mapper.mapList(restaurantService.listAllRestaurants(pageable), RestaurantResponse.class);
        return new ResponseEntity<>(responseList, HttpStatus.FOUND);
    }

    @PutMapping("/delete/{restaurantId}")
    @Operation(summary = "delete Restaurant", description = "delete Restaurant")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Integer restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return new ResponseEntity<>("restaurant with id '"+ restaurantId +"' deleted successfully"
                ,HttpStatus.ACCEPTED);
    }

}
