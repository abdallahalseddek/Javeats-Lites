package com.javaeat.services.impl;

import com.javaeat.enums.CartStatus;
import com.javaeat.enums.ErrorMessage;
import com.javaeat.exception.NotFoundException;
import com.javaeat.model.*;
import com.javaeat.repository.CartItemRepository;
import com.javaeat.repository.CartRepository;
import com.javaeat.repository.CustomerRepository;
import com.javaeat.repository.MenuItemRepository;
import com.javaeat.request.CartItemRequest;
import com.javaeat.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
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
        cartItem.setMenuItem(menuItemRepository.findById(cartItemRequest.getMenuItemId()).get());
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

    @PostConstruct
    void init() {

        Cart cart = new Cart();
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setState("CA");
        address.setGovernment("City");
        address.setContactNumber("555-1234");
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);

        Customer customer = new Customer();
        customer.setCart(cart);
        customer.setAddresses(addresses);

        cart.setTotalPrice(0.0);
        cart.setStatus(CartStatus.READ_WRITE);
        cart.setTotalItems(0);
        cart.setCustomer(customer);

        customerRepository.save(customer);
    }

}

