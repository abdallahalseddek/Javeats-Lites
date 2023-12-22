package com.javaeat.services;

import com.javaeat.model.CartItem;

public interface CartService {
    public void processCartItem(CartItem cartItem, Integer cartId);

}
