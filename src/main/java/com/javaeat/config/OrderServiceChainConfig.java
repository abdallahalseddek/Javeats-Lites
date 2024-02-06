package com.javaeat.config;

import com.javaeat.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderServiceChainConfig {


    private final CartServiceImpl cartService;
    private final MenuServiceImpl menuService;
    private final RestaurantServiceImpl restaurantService;
    private final PaymentServiceImpl paymentService;
    private final OrderServiceImp orderService;

    public OrderServiceChainConfig(
            CartServiceImpl cartService,
            MenuServiceImpl menuService,
            RestaurantServiceImpl restaurantService,
            PaymentServiceImpl paymentService,
            OrderServiceImp orderService) {
        this.cartService = cartService;
        this.menuService = menuService;
        this.restaurantService = restaurantService;
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

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
