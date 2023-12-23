package com.javaeat.services;

import com.javaeat.model.Cart;
import com.javaeat.model.CartItem;
import com.javaeat.request.CartItemRequest;
import com.javaeat.request.CartRequest;
import com.javaeat.response.CartItemResponse;
import com.javaeat.response.CartResponse;

import java.util.List;

public interface CartService {
    CartResponse addItemToCart(CartItemRequest itemRequest);
    void removeItem(Integer itemId);
    void removeAllCartItems(CartRequest request);

    List<CartItemResponse> listAllCartItems(CartRequest request);
    CartResponse checkCustomerHasCart(Integer customerId);

    // TODO: validate items availability method

    // TODO: move to checkout endpoint

    Cart mapToEntity(CartRequest request);

    CartRequest mapToRequest(Cart cart);

    CartItem mapToEntity(CartItemRequest request);

    CartItemRequest mapToRequest(CartItem cartItem);
    CartResponse mapToResponse(Cart cart);
    CartItemResponse mapToResponse(CartItem cartItem);
}
