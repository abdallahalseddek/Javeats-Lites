package com.javaeat.services;

import com.javaeat.model.Cart;
import com.javaeat.model.CartItem;
import com.javaeat.request.CartItemRequest;
import com.javaeat.request.CartRequest;

public interface CartService {
    void processCartItem(CartItem cartItem, Integer cartId);

    Cart mapToEntity(CartRequest dto);
    CartRequest mapToDto(Cart entity);
}
