package com.javaeat.controller;

import com.javaeat.enums.CartStatus;
import com.javaeat.request.CartItemRequest;
import com.javaeat.response.*;
import com.javaeat.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/cart")
@Slf4j
@Tag(name = "Cart Endpoints")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/list/all/{cartId}")
    @Operation(summary = "Endpoint that list all items in the cart",
            description = "Endpoint that list all items in the cart")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Cart Not Found Exception")
    public ResponseEntity<List<CartItemResponse>> browseCart(@PathVariable Integer cartId) {
        return ResponseEntity.ok(cartService.listAllCartItems(cartId));
    }

    @GetMapping("/status/{cartId}")
    @Operation(summary = "Endpoint that checks cart status",
            description = "Endpoint that retrieves the current status of the shopping cart, indicating whether it can be modified.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Cart Not Found Exception")
    public ResponseEntity<CartStatusResponse> getCartStatus(@PathVariable Integer cartId) {
        return ResponseEntity.ok(cartService.getCartStatus(cartId));
    }

    @PutMapping("/status")
    @Operation(summary = "Endpoint that updates cart status",
            description = "Endpoint that updates the current status of the shopping cart.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Cart Not Found Exception")
    public ResponseEntity<CartStatusResponse> updateCartStatus(@RequestParam Integer cartId,
                                                               @RequestParam CartStatus  newStatus) {
        return ResponseEntity.ok(cartService.updateCartStatus(cartId,newStatus));
    }

    @PostMapping("/item-availability")
    @Operation(summary = "Endpoint that checks the availability of items.",
            description = "Endpoint that checks the availability of items.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    public ResponseEntity<ItemsAvailabilityResponse> checkItemsAvailability(@Valid @RequestBody List<Integer> itemsIds) {
        // a method to call the service to check Items Availability in the stock.

        //return a fake status
        return ResponseEntity.ok(ItemsAvailabilityResponse.builder().build());
    }

    @PatchMapping("/update")
    @Operation(summary = "Endpoint that modifies cart.",
            description = "Endpoint that modifies the cart.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Cart items Not Found")
    public ResponseEntity<CartItemResponse> updateCartItem(@Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.updateCartItem(request));
    }


    @DeleteMapping("/remove/{itemId}")
    @Operation(summary = "Endpoint that delete cart item ",
            description = "Endpoint that delete cart item")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Item Not Found Exception")
    public ResponseEntity<String> deleteCartItem(@PathVariable Integer itemId) {
        cartService.removeItem(itemId);
        return ResponseEntity.ok("Item Deleted successfully");
    }

    @PostMapping("/additem")
    @Operation(summary = "Add item to cart", description = "Add item to cart")
    @ApiResponse(responseCode = "201", description = "Successful operation",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class)))
    @ApiResponse(responseCode = "400", description = "Bad request. Invalid input data")
    @ApiResponse(responseCode = "404", description = "Customer not found or Item not Found")
    @ApiResponse(responseCode = "500", description = "Internal server error. Something went wrong")
    ResponseEntity<CartResponse> addToCart(@RequestBody CartItemRequest request) {
        return ResponseEntity.ok(cartService.addItemToCart(request));
    }

}
