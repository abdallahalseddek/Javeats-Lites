package com.javaeat.handler.order;

import com.javaeat.enums.CartStatus;
import com.javaeat.exception.HandlerException;
import com.javaeat.model.Cart;
import com.javaeat.repository.CartRepository;
import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
@AllArgsConstructor
public class CartLockCheckHandler extends OrderHandler {

    private final CartRepository cartRepository;



    @Override
    public OrderResponse handle(OrderRequest request, OrderResponse response) {
        Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(() -> new HandlerException("cart with ID " + request.getCartId() + " is not available."));
        log.info("cart status: {}",cart.getStatus());
        if (CartStatus.READ_ONLY.equals(cart.getStatus())) {
            log.info("Cart is locked. Cannot proceed with the order.");
            throw new HandlerException("Cart is locked. Cannot proceed with the order.");
        }
        return handleNext(request,response);
    }
}
