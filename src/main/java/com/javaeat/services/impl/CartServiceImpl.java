package com.javaeat.services.impl;

import com.javaeat.enums.CartStatus;
import com.javaeat.model.Cart;
import com.javaeat.model.CartItem;
import com.javaeat.repository.CartItemRepository;
import com.javaeat.repository.CartRepository;
import com.javaeat.request.CartRequest;
import com.javaeat.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;
@Service
public class CartServiceImpl implements CartService {
    CartRepository cartRepository;
    EntityManager entityManager;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository,EntityManager entityManager) {
        this.cartRepository = cartRepository;
        this.entityManager=entityManager;
    }
@Transactional
    @Override
    public void processCartItem(CartItem cartItem, Integer cartId) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        // Check if the Cart exists and cart status is read_write
        if (optionalCart.isPresent() && optionalCart.get().getStatus().equals(CartStatus.READ_WRITE)) {

            // If the Cart exists, add the CartItem to it
            Cart cart = optionalCart.get();

            cart.addCartItem(cartItem);
            cartRepository.save(cart);// Save the updated Cart

        } else {
            // If the Cart doesn't exist, create a new Cart and add the CartItem to it
            Cart newCart = new Cart(/* initialize Cart attributes as needed */);
            newCart.addCartItem(cartItem);
            cartRepository.save(newCart); // Save the new Cart
        }
    }

    @Override
    public Cart mapToEntity(CartRequest dto) {
        return null;
    }

    @Override
    public CartRequest mapToDto(Cart entity) {
        return null;
    }


}

