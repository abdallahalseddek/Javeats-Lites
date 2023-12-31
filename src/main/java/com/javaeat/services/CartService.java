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

    DeleteCartResponse deleteCartItem(Integer itemId);

    void clearCart(Integer cartId);
    CartItemResponse updateCartItem(CartItemRequest cartItemRequest);
    List<CartItemResponse> browseCart(Integer cartId);

    CartStatusResponse getCartStatus(Integer cartId);

    CartStatusResponse updateCartStatus(Integer cartId, CartStatus newStatus);

    // CartStatusResponse updateCartStatus(Integer cartId, CartStatus status);

   // ItemAvailabilityResponse checkItemAvailable(Integer itemId);


}
