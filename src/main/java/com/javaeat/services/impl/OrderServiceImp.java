package com.javaeat.services.impl;

import com.javaeat.handler.order.*;
import com.javaeat.model.Cart;
import com.javaeat.repository.*;
import com.javaeat.request.OrderRequest;
import com.javaeat.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImp implements OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    @Override
    public boolean createOrder(OrderRequest request) {

        // set the chain of responsibilities
        OrderHandler orderHandler = OrderHandler.link(
                  new CartLockCheckHandler(cartRepository)
                , new ItemsAvailabilityCheckHandler(cartItemRepository)
                , new RestaurantWorkingHoursCheckHandler(restaurantRepository)
                , new PaymentProcessHandler(paymentRepository,cartRepository)
                , new FinalizeOrderHandler(orderRepository,cartRepository));

        return orderHandler.handle(request);
    }


}
