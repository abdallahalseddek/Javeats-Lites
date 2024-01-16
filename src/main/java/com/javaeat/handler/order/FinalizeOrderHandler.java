package com.javaeat.handler.order;

import com.javaeat.enums.OrderStatus;
import com.javaeat.model.Order;
import com.javaeat.repository.CartRepository;
import com.javaeat.repository.OrderRepository;
import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Date;

@Builder
@Slf4j
@AllArgsConstructor
public class FinalizeOrderHandler extends OrderHandler {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Override
    public OrderResponse handle(OrderRequest request, OrderResponse response) {
        //clear cart
        cartRepository.deleteById(request.getCartId());
        // save the order
        Order order = orderRepository.save(Order.builder().build());
        response.setOrderId(order.getOrderId());
        response.setCreatedAt(Date.from(Instant.now()));
        response.setOrderStatus(OrderStatus.PURCHASED);
        log.info("Order has been placed successfully.");
        return handleNext(request,response);
    }
}
