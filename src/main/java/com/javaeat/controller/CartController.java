package com.javaeat.controller;

import com.javaeat.enums.CartStatus;
import com.javaeat.request.CartItemRequest;
import com.javaeat.response.CartResponse;
import com.javaeat.response.CartStatusResponse;
import com.javaeat.response.DeleteCartResponse;
import com.javaeat.response.ItemsAvailabilityResponse;
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

    @GetMapping("/status/{cartId}")
    @Operation(summary = "Endpoint that checks cart status",
            description = "Endpoint that retrieves the current status of the shopping cart, indicating whether it can be modified.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Cart Not Found Exception")
    public ResponseEntity<CartStatusResponse> getCartStatus(@PathVariable Integer cartId) {
        // a method to call the service to get the status

        //return a fake status
        return ResponseEntity.ok(CartStatusResponse.builder().cartId(cartId).cartStatus(CartStatus.READ_ONLY).build());
    }

    @PutMapping("/status")
    @Operation(summary = "Endpoint that updates cart status",
            description = "Endpoint that updates the current status of the shopping cart.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Cart Not Found Exception")
    public ResponseEntity<CartStatusResponse> updateCartStatus(@RequestParam Integer cartId, @RequestParam CartStatus  newStatus) {
        // a method to call the service to get the status

        //return a fake status
        return ResponseEntity.ok(CartStatusResponse.builder().cartId(cartId).cartStatus(newStatus).build());
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

    @PatchMapping("/modify")
    @Operation(summary = "Endpoint that modifies cart.",
            description = "Endpoint that modifies the cart.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Cart items Not Found")
    public ResponseEntity<CartResponse> modifyCart(@Valid @RequestBody CartItemRequest request) {
        // a method to call the service to modify the cart items.

        //return a fake status
        return ResponseEntity.ok(CartResponse.builder().build());
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
