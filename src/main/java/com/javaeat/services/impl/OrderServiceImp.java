package com.javaeat.services.impl;

import com.javaeat.enums.CartStatus;
import com.javaeat.exception.HandlerException;
import com.javaeat.exception.NotFoundException;
import com.javaeat.handler.order.*;
import com.javaeat.model.Cart;
import com.javaeat.model.Order;
import com.javaeat.repository.*;
import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import com.javaeat.response.OrderStatusResponse;
import com.javaeat.services.OrderService;
import com.javaeat.util.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImp implements OrderService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final MapperUtil mapperUtil;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest request) {

        // set the chain of responsibilities
        OrderHandler orderHandler = OrderHandler.processOrder(
                  new CartLockCheckHandler(cartRepository)
                , new ItemsAvailabilityCheckHandler(menuItemRepository)
                , new RestaurantWorkingHoursCheckHandler(restaurantRepository)
                , new PaymentProcessHandler(paymentRepository,cartRepository)
                , new FinalizeOrderHandler(orderRepository,cartRepository,paymentRepository,restaurantRepository,mapperUtil));

        OrderResponse response = OrderResponse.builder()
                .customerId(request.getCustomerId())
                .restaurantId(request.getRestaurantId())
                .deliveryId(request.getDeliveryId())
                .items(request.getItems())
                .deliveryAddress(request.getDeliveryAddress())
                .build();


        return orderHandler.handle(request,response);
    }

    @Override
    public OrderResponse getOrder(Integer orderId) {
        Order order = getOrderById(orderId);
        return mapperUtil.mapEntity(order, OrderResponse.class);
    }
    @Override
    @Transactional
    public OrderStatusResponse updateStatus(Integer orderId) {
        Order order = getOrderById(orderId);
        order.updateStatus();
        Order savedOrder = orderRepository.save(order);
        return buildNewStatusResponse(savedOrder);
    }

    @Override
    public OrderStatusResponse getStatus(Integer orderId) {
        Order order = getOrderById(orderId);
        return buildNewStatusResponse(order);
    }

    @Override
    @Transactional
    public OrderStatusResponse cancel(Integer orderId) {
        Order order = getOrderById(orderId);
        order.cancelOrder();
        Order savedOrder = orderRepository.save(order);
        return buildNewStatusResponse(savedOrder);
    }

    private static OrderStatusResponse buildNewStatusResponse(Order savedOrder) {
        return OrderStatusResponse.builder().orderId(savedOrder.getOrderId()).status(savedOrder.getOrderStatus()).build();
    }


    private Order getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(404, "Cannot find order with this id"));
    }

//    private void handleCartLock(OrderRequest request){
//        Cart cart = cartRepository.findById(request.getCartId()).orElseThrow(() -> new HandlerException("cart with ID " + request.getCartId() + " is not available."));
//        log.info("cart status: {}",cart.getStatus());
//        if (CartStatus.READ_ONLY.equals(cart.getStatus())) {
//            log.info("Cart is locked. Cannot proceed with the order.");
//            throw new HandlerException("Cart is locked. Cannot proceed with the order.");
//        }
//    }
}
