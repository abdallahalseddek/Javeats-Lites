package com.javaeat.config;

import com.javaeat.handler.order.OrderHandler;
import com.javaeat.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderServiceChainConfig {


    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private MenuServiceImpl menuService;
    @Autowired
    private RestaurantServiceImpl restaurantService;
    @Autowired
    private PaymentServiceImpl paymentService;
    @Autowired
    private OrderServiceImp orderService;

    @Bean
    public OrderHandler orderHandlerChain() {
        // set the chain of responsibilities
        return OrderHandler.processOrder(
                  cartService
                , menuService
                , restaurantService
                , paymentService
                , orderService); // Return the first handler in the chain
    }
}
