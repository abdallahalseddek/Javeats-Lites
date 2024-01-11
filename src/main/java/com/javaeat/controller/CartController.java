package com.javaeat.controller;

import com.javaeat.enums.CartStatus;
import com.javaeat.request.CartItemRequest;
import com.javaeat.response.CartItemResponse;
import com.javaeat.response.CartResponse;
import com.javaeat.response.CartStatusResponse;
import com.javaeat.response.ItemAvailabilityResponse;
import com.javaeat.services.CartService;
import com.javaeat.util.MapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/cart")
@Slf4j
@Tag(name = "Cart Endpoints")
@AllArgsConstructor
public class CartController {
    private final CartService cartService;
    private final MapperUtil mapper;

    @PostMapping("/add/cartItem")
    @Operation(summary = "Add item to cart", description = "Add item to cart")
    @ApiResponse(responseCode = "201", description = "Successful operation",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Integer.class)))
    @ApiResponse(responseCode = "400", description = "Bad request. Invalid input data")
    @ApiResponse(responseCode = "404", description = "Customer not found or Item not Found")
    @ApiResponse(responseCode = "500", description = "Internal server error. Something went wrong")
    ResponseEntity<CartResponse> addItemToCart(@RequestBody @Valid CartItemRequest request) {
        CartResponse response = mapper.mapEntity(cartService.addItemToCart(request), CartResponse.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/update/cartItem")
    @Operation(summary = "Endpoint that modifies cart.",
            description = "Endpoint that modifies the cart.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Cart items Not Found")
    public ResponseEntity<CartItemResponse> updateCartItem(@RequestBody @Valid CartItemRequest cartItemRequest) {
        CartItemResponse response = mapper.mapEntity(cartService.updateCartItem(cartItemRequest), CartItemResponse.class);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/cartItem/{itemId}")
    @Operation(summary = "Endpoint that delete cart item ",
            description = "Endpoint that delete cart item")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Item Not Found Exception")
    public ResponseEntity<String> deleteCartItem(@PathVariable Integer itemId) {
        cartService.deleteCartItem(itemId);
        return ResponseEntity.ok("cart items with id ' " + itemId + "' Deleted successfully");
    }

    @DeleteMapping("/clear/{cartId}")
    @Operation(summary = "Endpoint that delete all cart items of specific cart ",
            description = "Endpoint that delete all cart items of specific cart related to customer")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Item Not Found Exception")
    public ResponseEntity<String> clearCart(@PathVariable Integer cartId) {
        cartService.clearCart(cartId);
        return ResponseEntity.ok("all cart items of cart '"  + cartId + "' Deleted successfully");
    }

    @GetMapping("/browse-cart/{cartId}")
    @Operation(summary = "Endpoint that list all items in the cart",
            description = "Endpoint that list all items in the cart")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Cart Not Found Exception")
    public ResponseEntity<List<CartItemResponse>> browseCart(@PathVariable Integer cartId) {
        List<CartItemResponse> responseList = mapper.mapList(cartService.browseCart(cartId), CartItemResponse.class);
        return new ResponseEntity<>(responseList, HttpStatus.FOUND);
    }

    @GetMapping("/status/{cartId}")
    @Operation(summary = "Endpoint that checks cart status",
            description = "Endpoint that retrieves the current status of the shopping cart, indicating whether it can be modified.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Cart Not Found Exception")
    public ResponseEntity<CartStatusResponse> getCartStatus(@PathVariable Integer cartId) {
        CartStatusResponse response = mapper.mapEntity(cartService.getCartStatus(cartId), CartStatusResponse.class);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PutMapping("/status")
    @Operation(summary = "Endpoint that updates cart status",
            description = "Endpoint that updates the current status of the shopping cart.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Cart Not Found Exception")
    public ResponseEntity<CartStatusResponse> updateCartStatus(@RequestParam @Valid Integer cartId,
                                                               @RequestParam @Valid CartStatus newStatus) {
        CartStatusResponse response = mapper.mapEntity(cartService.updateCartStatus(cartId, newStatus), CartStatusResponse.class);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PostMapping("/item-availability")
    @Operation(summary = "Endpoint that checks the availability of items.",
            description = "Endpoint that checks the availability of items.")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    public ResponseEntity<ItemAvailabilityResponse> checkItemAvailability(@RequestParam @Valid Integer itemsId) {
        ItemAvailabilityResponse response = mapper.mapEntity(cartService.checkItemAvailable(itemsId),
                ItemAvailabilityResponse.class);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }


}
