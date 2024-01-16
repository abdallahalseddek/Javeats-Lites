package com.javaeat.services.impl;

import com.javaeat.handler.order.*;
import com.javaeat.model.Cart;
import com.javaeat.repository.*;
import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
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
    public OrderResponse createOrder(OrderRequest request) {

        // set the chain of responsibilities
        OrderHandler orderHandler = OrderHandler.link(
                  new CartLockCheckHandler(cartRepository)
                , new ItemsAvailabilityCheckHandler(cartItemRepository)
                , new RestaurantWorkingHoursCheckHandler(restaurantRepository)
                , new PaymentProcessHandler(paymentRepository,cartRepository)
                , new FinalizeOrderHandler(orderRepository,cartRepository));

        OrderResponse response = OrderResponse.builder()
                .customerId(request.getCustomerId())
                .restaurantId(request.getRestaurantId())
                .deliveryId(request.getDeliveryId())
                .items(request.getItems())
                .deliveryAddress(request.getDeliveryAddress())
                .build();


        return orderHandler.handle(request,response);
    }


}
