package com.javaeat.services;

import com.javaeat.enums.CartStatus;
import com.javaeat.model.Cart;
import com.javaeat.model.CartItem;
import com.javaeat.request.CartItemRequest;
import com.javaeat.request.CartRequest;
import com.javaeat.response.*;

import java.util.List;

public interface CartService {
    CartResponse addItemToCart(CartItemRequest itemRequest);

    DeleteCartResponse removeItem(Integer itemId);

    void removeAllCartItems(Integer cartId);

    List<CartItemResponse> listAllCartItems(Integer cartId);

    void moveItemsToCheckout(CartRequest request);

    CartStatusResponse getCartStatus(Integer cartId);

    CartStatusResponse updateCartStatus(Integer cartId, CartStatus status);

    ItemAvailabilityResponse checkItemAvailable(Integer itemId);

    Cart mapToEntity(CartRequest request);

    CartItem mapToEntity(CartItemRequest request);

    CartRequest mapToRequest(Cart cart);

    CartItemRequest mapToRequest(CartItem cartItem);

    CartResponse mapToResponse(Cart cart);

    CartItemResponse mapToResponse(CartItem cartItem);
}
