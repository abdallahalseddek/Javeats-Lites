package com.javaeat.services;

import com.javaeat.enums.CartStatus;
import com.javaeat.model.Cart;
import com.javaeat.model.CartItem;
import com.javaeat.repository.CartItemRepository;
import com.javaeat.repository.CartRepository;
import com.javaeat.request.CartItemRequest;
import com.javaeat.request.CartRequest;
import com.javaeat.response.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper mapper;

    @Bean
    void init() {
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
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
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
    public CartItemResponse updateCartItem(CartItemRequest itemRequest) {
        var item = cartItemRepository.findById(itemRequest.getId())
                .orElseThrow(() -> new EntityNotFoundException("Not Found Item"));
        item.setQuantity(itemRequest.getQuantity());
        item.setTotalPrice(item.getQuantity() * itemRequest.getUnitPrice());
        cartItemRepository.save(item);
        return mapToResponse(item);
    }

    @Override
    public DeleteCartResponse removeItem(Integer itemId) {
        var item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Item"));
        cartItemRepository.delete(item);
        DeleteCartResponse response = mapper.map(item, DeleteCartResponse.class);
        response.setIsDeleted(true);
        response.setNote("Item with Id '" + itemId + "' is deleted successfully");
        return response;
    }

    @Override
    public void removeAllCartItems(CartRequest request) {

    }

    @Override
    public List<CartItemResponse> listAllCartItems(Integer cartId) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Entity"));
        return cart.getCartItems()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CartResponse checkCustomerHasCart(Integer customerId) {
        return null;
    }

    @Override
    public CartStatusResponse getCartStatus(Integer cartId) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Cart"));
        return mapper.map(cart, CartStatusResponse.class);
    }

    @Override
    public CartStatusResponse updateCartStatus(Integer cartId, CartStatus status) {

        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Cart"));

        // TODO: handle invalid given status

        cart.setUpdatedAt(LocalDateTime.now());
        cart.setStatus(status);
        var savedCart = cartRepository.save(cart);

        return mapper.map(savedCart, CartStatusResponse.class);
    }

    @Override
    public ItemAvailabilityResponse checkItemAvailable(Integer itemId) {
        return null;
    }

    @Override
    public void moveToCheckout(Integer cartId) {

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

