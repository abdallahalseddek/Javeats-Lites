package com.javaeat.services.impl;

import com.javaeat.enums.CartStatus;
import com.javaeat.enums.ErrorMessage;
import com.javaeat.exception.HandlerException;
import com.javaeat.exception.NotFoundException;
import com.javaeat.model.Cart;
import com.javaeat.model.CartItem;
import com.javaeat.model.Customer;
import com.javaeat.model.MenuItem;
import com.javaeat.repository.CartItemRepository;
import com.javaeat.repository.CartRepository;
import com.javaeat.repository.CustomerRepository;
import com.javaeat.repository.MenuItemRepository;
import com.javaeat.request.CartItemRequest;
import com.javaeat.request.CartRequest;
import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import com.javaeat.services.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@EnableCaching
public class CartServiceImpl extends OrderHandler implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final MenuItemRepository menuItemRepository;
    private final LocalDateTime dateTime = LocalDateTime.now();

    @Transactional
    @Override
    public Cart addItemToCart(CartItemRequest cartItemRequest) {
        Cart cart = getCartById(cartItemRequest.getCartId());
        CartItem cartItem = CartItem.buildCartItem(cartItemRequest);
        cartItem.setCart(cart);
        cart.setCreationTime(dateTime);
        updateCartDetails(cartItem, cart);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public CartItem updateCartItem(CartItemRequest cartItemRequest) {
        Cart cart = getCartById(cartItemRequest.getId());
        CartItem cartItem = getCartItemById(cartItemRequest.getCartId());
        cartItem.setUnitPrice(cartItemRequest.getUnitPrice());
        cartItem.setQuantity(cartItemRequest.getQuantity());
        updateCartDetails(cartItem, cart);
        cart.setLastUpdatedTime(dateTime);
        return cartItemRepository.save(cartItem);
    }

    @Transactional
    @Override
    public void deleteCartItem(Integer itemId) {
        CartItem cartItem = getCartItemById(itemId);
        cartItem.getCart().setLastUpdatedTime(dateTime);
        cartItemRepository.delete(cartItem);
    }

    @Transactional
    @Override
    public void clearCart(Integer cartId) {
        Cart cart = getCartById(cartId);
        cartItemRepository.deleteByCartId(cartId);
        cart.setTotalPrice(0.0);
        cart.setTotalItems(0);
    }

    @Cacheable("items")
    @Override
    public List<CartItem> browseCart(Integer cartId) {
        Cart cart = getCartById(cartId);
        return cartItemRepository.findAllByCart(cart);
    }

    @Override
    public Cart getCartStatus(Integer cartId) {
        return getCartById(cartId);
    }

    @Override
    public Cart updateCartStatus(Integer cartId, CartStatus newStatus) {
        Cart cart = getCartById(cartId);
        cart.setStatus(newStatus);
        cart.setLastUpdatedTime(dateTime);
        return cartRepository.save(cart);
    }

    @Override
    public MenuItem checkItemAvailable(Integer itemsId) {
        return menuItemRepository.findById(itemsId).get();
    }

    @Override
    public Cart createCart(CartRequest cartRequest) {
        Customer customer=customerRepository.findById(cartRequest.getCustomerId())
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(),
                ErrorMessage.CUSTOMER_NOT_FOUND.name()));
        Cart newCart = Cart.buildCart(cartRequest);
        newCart.setCustomer(customer);

        return cartRepository.save(newCart);
    }

    private Cart getCartById(Integer cartId) {
        return cartRepository.findById(cartId).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(),
                        ErrorMessage.CART_NOT_FOUND.name()));
    }

    private CartItem getCartItemById(Integer cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(),
                        ErrorMessage.CART_ITEM_NOT_FOUND.name()));
    }

    private void updateCartDetails(CartItem cartItem, Cart cart) {
        int oldCartItemQuantity = cartItem.getQuantity();
        double oldCartItemTotalPrice = cartItem.getTotalPrice();
        cartItem.setTotalPrice(cartItem.getUnitPrice() * cartItem.getQuantity());
        CalculateCartTotalPriceAndTotalItems(cartItem, cart, oldCartItemTotalPrice, oldCartItemQuantity);
    }

    private void CalculateCartTotalPriceAndTotalItems(CartItem cartItem, Cart cart, double oldCartItemTotalPrice, int oldCartItemQuantity) {
        cart.setTotalPrice(cart.getTotalPrice() - oldCartItemTotalPrice + cartItem.getTotalPrice());
        cart.setTotalItems(cart.getTotalItems() - oldCartItemQuantity + cartItem.getQuantity());
        cart.getCartItems().add(cartItem);
    }

    @Override
    public OrderResponse handleOrder(OrderRequest request, OrderResponse response) {
        return null;
    }

    public OrderResponse handle(OrderRequest request, OrderResponse response) {
        Cart cart = getCart(request);
        log.info("cart status: {}",cart.getStatus());
        if (CartStatus.READ_ONLY.equals(cart.getStatus())) {
            log.info("Cart is locked. Cannot proceed with the order.");
            throw new HandlerException("Cart is locked. Cannot proceed with the order.");
        }

        log.info("move to the next handler (items validation), {}", getNext());
        return handleNext(request,response);
    }

    private Cart getCart(OrderRequest request) {
        return cartRepository.findById(request.getCartId()).orElseThrow(() -> new HandlerException("cart with ID " + request.getCartId() + " is not available."));
    }
}

