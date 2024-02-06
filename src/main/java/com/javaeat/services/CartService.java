package com.javaeat.services;

import com.javaeat.enums.CartStatus;
import com.javaeat.model.Cart;
import com.javaeat.model.CartItem;
import com.javaeat.model.MenuItem;
import com.javaeat.request.CartItemRequest;
import com.javaeat.request.CartRequest;
import com.javaeat.response.*;

import java.util.List;

public interface CartService {
    Cart addItemToCart(CartItemRequest itemRequest);

    void deleteCartItem(Integer itemId);

    void clearCart(Integer cartId);
    CartItem updateCartItem(CartItemRequest cartItemRequest);
    List<CartItem> browseCart(Integer cartId);

    Cart getCartStatus(Integer cartId);

    Cart updateCartStatus(Integer cartId, CartStatus newStatus);
    MenuItem checkItemAvailable(Integer itemsId);

    Cart createCart(CartRequest cartRequest);
}
