package com.javaeat.handler.order;

import com.javaeat.enums.CartStatus;
import com.javaeat.model.Cart;
import com.javaeat.repository.CartRepository;
import com.javaeat.request.OrderRequest;
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
    public boolean handle(OrderRequest request) {
        Cart cart = cartRepository.findById(request.getCartId()).orElseThrow();
        if (CartStatus.READ_ONLY.equals(cart.getStatus())) {
            log.info("Cart is locked. Cannot proceed with the order.");
            return false;
        }
        return handleNext(request);
    }
}
