package com.javaeat.services;

import com.javaeat.enums.CartStatus;
import com.javaeat.model.Cart;
import com.javaeat.model.CartItem;
import com.javaeat.request.CartItemRequest;
import com.javaeat.request.CartRequest;
import com.javaeat.response.CartItemResponse;
import com.javaeat.response.CartResponse;
import com.javaeat.response.*;


import java.util.List;

public interface CartService {
    CartResponse addItemToCart(CartItemRequest itemRequest);

    CartItemResponse updateCartItem(CartItemRequest itemRequest);
    void removeAllCartItems(Integer cartId);

    List<CartItemResponse> listAllCartItems(Integer cartId);
    void moveItemsToCheckout(CartRequest request ,Integer cartId);
    CartResponse checkCustomerHasCart(Integer customerId);

    // TODO: validate items availability method

    // TODO: move to checkout endpoint


    CartStatusResponse getCartStatus(Integer cartId);

    CartStatusResponse updateCartStatus(Integer cartId, CartStatus status);

    ItemAvailabilityResponse checkItemAvailable(Integer itemId);

    Cart mapToEntity(CartRequest request);

    CartRequest mapToRequest(Cart cart);

    CartItem mapToEntity(CartItemRequest request);

    CartItemRequest mapToRequest(CartItem cartItem);

    CartResponse mapToResponse(Cart cart);

    CartItemResponse mapToResponse(CartItem cartItem);
    DeleteCartResponse removeItem(Integer itemId);
}
