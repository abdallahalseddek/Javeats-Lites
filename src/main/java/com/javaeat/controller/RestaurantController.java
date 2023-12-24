package com.javaeat.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/restaurant")
@Slf4j
@Tag(name = "Restaurant Endpoints")
public class RestaurantController {

    @GetMapping("/{restaurantId}")
    @Operation(summary = "Check Restaurant acceptance.",
            description = "Endpoint that checks whether it accepts orders or not.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Order Not Found Exception")
    public ResponseEntity<Boolean> checkRestaurantAcceptance(@PathVariable Integer restaurantId) {

        //return a fake status
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
