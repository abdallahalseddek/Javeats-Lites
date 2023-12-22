package com.javaeat.services.impl;

import com.javaeat.model.CartItem;
import com.javaeat.repository.CartItemRepository;
import com.javaeat.request.CartItemRequest;
import com.javaeat.services.CartItemService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {
    CartItemRepository cartItemRepository;
    private final ModelMapper modelMapper;



    public CartItemServiceImpl(ModelMapper modelMapper ,CartItemRepository cartItemRepository) {
        this.modelMapper = modelMapper;
        this.cartItemRepository =cartItemRepository;
    }

    public CartItem convertToEntity(CartItemRequest cartItemDto) {
        return modelMapper.map(cartItemDto, CartItem.class);
    }
}
