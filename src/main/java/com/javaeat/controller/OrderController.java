package com.javaeat.controller;

import com.javaeat.enums.OrderStatus;
import com.javaeat.request.OrderRequest;
import com.javaeat.response.DeleteOrderResponse;
import com.javaeat.response.OrderResponse;
import com.javaeat.response.OrderStatusResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/order")
@Slf4j
@Tag(name = "Place An Order Endpoints")
public class OrderController {

    @PostMapping
    @Operation(summary = "Endpoint that creates an order.",
            description = "Endpoint that creates an order.")
    @ApiResponse(responseCode = "201", description = "Order has been created Successfully")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        // a method to call the service to create the order

        //return a fake status
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.builder().build());
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order.",
            description = "Endpoint that retrieves all order details")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Order Not Found Exception")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Integer orderId) {
        // a method to call the service to get the order details

        //return a fake status
        return ResponseEntity.ok(OrderResponse.builder().build());
    }


    @DeleteMapping("/cancel/{orderId}")
    @Operation(summary = "Endpoint that cancel order.",
            description = "Endpoint that cancel the order.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Order Not Found Exception")
    public ResponseEntity<DeleteOrderResponse> cancelOrder(@PathVariable Integer orderId) {
        // a method to call the service to cancel the order

        //return a fake status
        return ResponseEntity.ok(DeleteOrderResponse.builder().orderId(orderId).isDeleted(Boolean.FALSE).note("Cannot cancel it, Order has been accepted by the restaurant.").build());
    }

    @GetMapping("/status/{orderId}")
    @Operation(summary = "Endpoint that checks order status",
            description = "Endpoint that retrieves the current status of the order.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Order Not Found Exception")
    public ResponseEntity<OrderStatusResponse> getOrderStatus(@PathVariable Integer orderId) {
        // a method to call the service to get the status

        //return a fake status
        return ResponseEntity.ok(OrderStatusResponse.builder().orderId(orderId).status(OrderStatus.PURCHASED).build());
    }

    @PutMapping("/status")
    @Operation(summary = "Endpoint that updates order status",
            description = "Endpoint that updates the current order of the shopping cart.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Cart Not Found Exception")
    public ResponseEntity<OrderStatusResponse> updateCartStatus(@RequestParam Integer orderId, @RequestParam OrderStatus newStatus) {
        // a method to call the service to update the status

        //return a fake status
        return ResponseEntity.ok(OrderStatusResponse.builder().orderId(orderId).status(newStatus).build());
    }


}
