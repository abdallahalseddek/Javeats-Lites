package com.javaeat.services;

import com.javaeat.model.CartItem;
import com.javaeat.request.CartItemRequest;


public interface CartItemService {
    CartItem mapToEntity(CartItemRequest cartItemDto);
    CartItemRequest mapToDto(CartItem entity);

}
