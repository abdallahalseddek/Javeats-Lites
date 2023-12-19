package com.javaeat.controller;

import com.javaeat.enums.CartStatus;
import com.javaeat.request.ItemsAvailabilityRequest;
import com.javaeat.response.CartStatusResponse;
import com.javaeat.response.ItemsAvailabilityResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/order")
@Slf4j
@Tag(name = "Place An Order Endpoints")
public class PlaceOrderController {

    @GetMapping("/cart-status/{cartId}")
    @Operation(summary = "Endpoint that checks cart status",
            description = "Endpoint that retrieves the current status of the shopping cart, indicating whether it can be modified.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Cart Not Found Exception")
    @ApiResponse(responseCode = "500", description = "Server Error")
    public ResponseEntity<CartStatusResponse> getCartStatus(@PathVariable Integer cartId) {
        // a method to call the service to get the status

        //return a fake status
        return ResponseEntity.ok(CartStatusResponse.builder().cartId(cartId).cartStatus(CartStatus.READ_ONLY).build());
    }

    @PostMapping("/item-availability")
    @Operation(summary = "Endpoint that checks the availability of items.",
            description = "Endpoint that checks the availability of items.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "500", description = "Server Error")
    public ResponseEntity<ItemsAvailabilityResponse> checkItemsAvailability(@Valid @RequestBody ItemsAvailabilityRequest request) {
        // a method to call the service to check Items Availability

        //return a fake status
        return ResponseEntity.ok(ItemsAvailabilityResponse.builder().build());
    }

}
