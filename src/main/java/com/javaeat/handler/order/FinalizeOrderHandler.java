package com.javaeat.handler.order;

import com.javaeat.model.Order;
import com.javaeat.repository.CartRepository;
import com.javaeat.repository.OrderRepository;
import com.javaeat.request.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Builder
@Slf4j
@AllArgsConstructor
public class FinalizeOrderHandler extends OrderHandler {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Override
    public boolean handle(OrderRequest request) {
        //clear cart
        cartRepository.deleteById(request.getCartId());
        // save the order
        orderRepository.save(Order.builder().build());
        log.info("Order has been placed successfully.");
        return handleNext(request);
    }
}
