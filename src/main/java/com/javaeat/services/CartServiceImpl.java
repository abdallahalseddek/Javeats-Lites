package com.javaeat.services;

import com.javaeat.enums.CartStatus;
import com.javaeat.model.Cart;
import com.javaeat.model.CartItem;
import com.javaeat.repository.CartItemRepository;
import com.javaeat.repository.CartRepository;
import com.javaeat.request.CartItemRequest;
import com.javaeat.request.CartRequest;
import com.javaeat.response.CartItemResponse;
import com.javaeat.response.CartResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper mapper;
    @Override
    public CartResponse addItemToCart(CartItemRequest itemRequest) {
        // TODO: check the customer existence and so the cart
        // TODO: load the targeted cart to assign the items to it & update its attributes
        CartItem cartItem = mapToEntity(itemRequest);
        cartItem.setTotalPrice(cartItem.getQuantity() * cartItem.getUnitPrice());
        CartItem savedItem = cartItemRepository.save(cartItem);
        return mapToResponse(savedItem.getCart());
    }

    @Override
    public void removeItem(CartItemRequest itemRequest) {
    }

    @Override
    public void removeAllCartItems(CartRequest request) {

    }

    @Override
    public List<CartResponse> listAllCartItems(CartRequest request) {
        return null;
    }

    @Override
    public CartResponse checkCustomerHasCart(Integer customerId) {
        return null;
    }

    @Override
    public Cart mapToEntity(CartRequest request) {
        return mapper.map(request, Cart.class);
    }

    @Override
    public CartRequest mapToRequest(Cart cart) {
        return mapper.map(cart, CartRequest.class);
    }

    @Override
    public CartItem mapToEntity(CartItemRequest request) {
        return mapper.map(request, CartItem.class);
    }

    @Override
    public CartItemRequest mapToRequest(CartItem cartItem) {
        return mapper.map(cartItem, CartItemRequest.class);
    }

    @Override
    public CartResponse mapToResponse(Cart cart) {
        return mapper.map(cart, CartResponse.class);
    }

    @Override
    public CartItemResponse mapToResponse(CartItem cartItem) {
        return mapper.map(cartItem, CartItemResponse.class);
    }
}

