package com.javaeat.services;

import com.javaeat.enums.CartStatus;
import com.javaeat.model.*;
import com.javaeat.repository.CartItemRepository;
import com.javaeat.repository.CartRepository;
import com.javaeat.repository.CheckoutRepository;
import com.javaeat.repository.CustomerRepository;
import com.javaeat.request.CartItemRequest;
import com.javaeat.request.CartRequest;
import com.javaeat.response.CartItemResponse;
import com.javaeat.response.CartResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ModelMapper mapper;
    private CheckoutRepository checkoutRepository;
    private CustomerRepository customerRepository;
    @PostConstruct
    void init(){
        // for development phase until implement the customer logic
        // when signup a customer, automatically cart is created
        Cart cart = new Cart();
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setState("CA");
        address.setGovernment("City");
        address.setContactNumber("555-1234");
        List<Address> addresses  =new ArrayList<>();
        addresses.add(address);


        Customer customer =new Customer();
        customer.setCart(cart);
        customer.setAddresses(addresses);


        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        cart.setTotalPrice(0.0);
        cart.setStatus(CartStatus.READ_WRITE);
        cart.setTotalItems(0);
        cart.setCustomer(customer);


        customerRepository.save(customer);
    }git
    @Override
    public CartResponse addItemToCart(CartItemRequest cartItemRequest) {
        Cart cart = cartRepository.findById(cartItemRequest.getCartId())
                .orElseThrow(()-> new EntityNotFoundException("Cart not found"));
        CartItem cartItem = mapToEntity(cartItemRequest) ;
        cartItem.setTotalPrice(cartItem.getQuantity() * cartItem.getUnitPrice());
        cart.setUpdatedAt(LocalDateTime.now());
        if (cart.getStatus() == CartStatus.READ_WRITE) {
            // Retrieve old cart item details if present
            Optional<CartItem> existingCartItem = cartItemRepository.findById(cartItem.getId());
            int oldQuantity = existingCartItem.map(CartItem::getQuantity).orElse(0);
            double oldTotalPrice = existingCartItem.map(CartItem::getTotalPrice).orElse(0.0);

            // Update cart details
            cart.setTotalPrice(cart.getTotalPrice() - oldTotalPrice + cartItem.getTotalPrice());
            cart.setTotalItems(cart.getTotalItems() - oldQuantity + cartItem.getQuantity());
            cart.getCartItems().add(cartItem);

            // Save the updated cart
            cartRepository.save(cart);
        }
        return mapToResponse(cart);
        // TODO: exception of exist item in the cart
    }

    @Override
    public CartItemResponse updateCartItem(CartItemRequest itemRequest) {
        var item = cartItemRepository.findById(itemRequest.getId())
                .orElseThrow(()->new EntityNotFoundException("Not Found Item"));
        item.setQuantity(itemRequest.getQuantity());
        item.setTotalPrice(item.getQuantity()*itemRequest.getUnitPrice());
        cartItemRepository.save(item);
        return mapToResponse(item);
    }

    @Override
    public void removeItem(Integer itemId) {
       var item = cartItemRepository.findById(itemId)
                .orElseThrow(()-> new EntityNotFoundException("Not Found Item"));
        cartItemRepository.delete(item);
    }



    @Override
    public void removeAllCartItems(Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(()-> new EntityNotFoundException("Cart not found"));
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public List<CartItemResponse> listAllCartItems(Integer cartId) {
        var cart = cartRepository.findById(cartId)
                .orElseThrow(()->new EntityNotFoundException("Not Found Entity"));
        return cart.getCartItems()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    public void moveItemsToCheckout(CartRequest request) {
        Cart cart = mapToEntity(request);
        cart= cartRepository.findById(cart.getId())
                     .orElseThrow(() -> new EntityNotFoundException("Cart not found"));
        // Create a new checkout entity and copy relevant information
        Checkout checkout = new Checkout();
        checkout.setCustomer(cart.getCustomer()); // Assuming a user is associated with the cart
        checkout.setCartItems(new ArrayList<>(cart.getCartItems())); // Copy items from cart
        checkout.setTotalPrice(cart.getTotalPrice()); // You might have a method to calculate the total price
        checkout.setShippingState(cart.getCustomer().getAddresses().get(0).getState());
        checkout.setShippingGovernment(cart.getCustomer().getAddresses().get(0).getGovernment());
        checkout.setShippingStreet(cart.getCustomer().getAddresses().get(0).getStreet());
        checkout.setShippingContactNumber(cart.getCustomer().getAddresses().get(0).getContactNumber());
        // Save the checkout entity
        checkoutRepository.save(checkout);

        // Optionally, you might want to clear the cart
        //cart.getCartItems().clear();
        cartRepository.save(cart);
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

