package com.javaeat.services.impl;

import com.javaeat.enums.CartStatus;
import com.javaeat.enums.ErrorMessage;
import com.javaeat.exception.NotFoundException;
import com.javaeat.model.*;
import com.javaeat.repository.CartItemRepository;
import com.javaeat.repository.CartRepository;
import com.javaeat.repository.CustomerRepository;
import com.javaeat.request.CartItemRequest;
import com.javaeat.response.*;
import com.javaeat.services.CartService;
import com.javaeat.util.MapperUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper mapper;
    private final CustomerRepository customerRepository;

    @Transactional
    @Override
    public CartResponse addItemToCart(CartItemRequest cartItemRequest) {
        Cart cart = getCartById(cartItemRequest.getCartId());
        //check if cart item exists or no
        Optional<CartItem> existingCartItem = cartItemRepository.findById(cartItemRequest.getId());
        existingCartItem.ifPresent(cartItem -> {
            throw new IllegalArgumentException(ErrorMessage.CART_ITEM_ALREADY_EXISTS.name());
        });
        // if cart item does not exist create new one
        CartItem cartItem =new CartItem();
        updateCartDetails(cartItemRequest,cartItem, cart);
        cartRepository.save(cart);
        return MapperUtil.mapToResponse(cart);
    }

    @Transactional
    @Override
    public CartItemResponse updateCartItem(CartItemRequest cartItemRequest) {
        //check  cart item existence
        CartItem cartItem =getCartItemById(cartItemRequest.getCartId());
        Cart cart = getCartById(cartItemRequest.getId());
        cart = updateCartDetails(cartItemRequest, cartItem, cart);
        //save cart and with cascade save cart items
        cartRepository.save(cart);
        return MapperUtil.mapToResponse(cartItem); // its not clear function
    }

    @Transactional
    @Override
    public DeleteCartResponse deleteCartItem(Integer itemId) {
        var item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Item"));
        cartItemRepository.delete(item);
        DeleteCartResponse response = mapper.map(item, DeleteCartResponse.class);
        response.setIsDeleted(true);
        response.setNote("Item with Id '" + itemId + "' is deleted successfully");
        return response;
    }

    @Transactional
    @Override
    public void clearCart(Integer cartId) {
        Cart cart = getCartById(cartId);
        cartItemRepository.deleteByCartId(cartId);
        //clear cart
        cart.setTotalPrice(0.0);
        cart.setTotalItems(0);
    }

    @Override
    public List<CartItemResponse> browseCart(Integer cartId) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Entity"));
        return MapperUtil.mapToCartItemsResponse(cart.getCartItems());
    }

    @Override
    public CartStatusResponse getCartStatus(Integer cartId) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found Cart"));
        return mapper.map(cart, CartStatusResponse.class);
    }

    @Override
    public CartStatusResponse updateCartStatus(Integer cartId, CartStatus newStatus) {
        return null;
    }

    private Cart getCartById(Integer cartId) {
        return cartRepository.findById(cartId).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), ErrorMessage.CART_NOT_FOUND.name()));
    }
    private CartItem getCartItemById(Integer cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(), ErrorMessage.CART_ITEM_NOT_FOUND.name()));
    }
    private static Cart updateCartDetails(CartItemRequest cartItemRequest, CartItem cartItem, Cart cart) {
        int oldCartItemQuantity = cartItem.getQuantity();
        double oldCartItemTotalPrice = cartItem.getTotalPrice();
        cartItem = MapperUtil.mapToEntity(cartItemRequest);
        cartItem.setTotalPrice(cartItem.getUnitPrice()* cartItem.getQuantity());
        //update cart
        CalculateCartTotalPriceAndTotalItems(cartItem, cart, oldCartItemTotalPrice, oldCartItemQuantity);
        return cart;
    }
    private static void CalculateCartTotalPriceAndTotalItems(CartItem cartItem, Cart cart, double oldCartItemTotalPrice, int oldCartItemQuantity) {
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

