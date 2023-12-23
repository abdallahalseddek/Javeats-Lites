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
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper mapper;

    @Bean
    void init(){
        // for development phase until implement the customer logic
        // when signup a customer, automatically cart is created
        Cart cart = new Cart();
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        cart.setStatus(CartStatus.READ_WRITE);
        cartRepository.save(cart);
    }
    @Override
    public CartResponse addItemToCart(CartItemRequest itemRequest) {
        var cart = cartRepository.findById(itemRequest.getCartId())
                .orElseThrow(()-> new EntityNotFoundException("Cart not found"));
        CartItem cartItem = mapToEntity(itemRequest);
        cartItem.setCart(cart);
        cartItem.setTotalPrice(cartItem.getQuantity() * cartItem.getUnitPrice());
        cart.getCartItems().add(cartItem);
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
        return mapToResponse(cart);
        // TODO: exception of exist item in the cart
    }

    @Override
    public CartResponse updateCartItem(CartItemRequest itemRequest) {
        return null;
    }

    @Override
    public void removeItem(Integer itemId) {
       var item = cartItemRepository.findById(itemId)
                .orElseThrow(()-> new EntityNotFoundException("Not Found Item"));
        cartItemRepository.delete(item);
    }

    @Override
    public void removeAllCartItems(CartRequest request) {

    }

    @Override
    public List<CartItemResponse> listAllCartItems(CartRequest request) {
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

