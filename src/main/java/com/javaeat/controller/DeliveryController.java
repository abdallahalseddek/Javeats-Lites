package com.javaeat.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/deliveries")
@Slf4j
@Tag(name = "Deliveries Endpoints")
public class DeliveryController {


    @GetMapping
    @Operation(summary = "Check Delivery acceptance.",
            description = "Endpoint that checks whether delivery service accepts orders or not.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Not Found Exception")

    public ResponseEntity<Boolean> checkDeliveryAcceptance(@RequestParam Integer restaurantId, @RequestParam String orderAddress) {
        // a method to call the service to get the order details

        //return a fake status
        return ResponseEntity.ok(Boolean.TRUE);
    }
}
