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

    CartItemResponse updateCartItem(CartItemRequest itemRequest);

    DeleteCartResponse removeItem(Integer itemId);

    void removeAllCartItems(CartRequest request);

    List<CartItemResponse> listAllCartItems(Integer cartId);

    CartResponse checkCustomerHasCart(Integer customerId);

    CartStatusResponse getCartStatus(Integer cartId);

    CartStatusResponse updateCartStatus(Integer cartId, CartStatus status);

    ItemAvailabilityResponse checkItemAvailable(Integer itemId);

    void moveToCheckout(Integer cartId);

    Cart mapToEntity(CartRequest request);

    CartRequest mapToRequest(Cart cart);

    CartItem mapToEntity(CartItemRequest request);

    CartItemRequest mapToRequest(CartItem cartItem);

    CartResponse mapToResponse(Cart cart);

    CartItemResponse mapToResponse(CartItem cartItem);
}
