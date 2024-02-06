package com.javaeat.services.impl;

import com.javaeat.enums.OrderStatus;
import com.javaeat.exception.NotFoundException;
import com.javaeat.model.Cart;
import com.javaeat.model.Order;
import com.javaeat.model.Payment;
import com.javaeat.model.Restaurant;
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
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImp extends OrderHandler implements OrderService {

    private final CartRepository cartRepository;
    private final RestaurantRepository restaurantRepository;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final MapperUtil mapperUtil;
    @Override
    @Transactional
    public OrderResponse handleOrder(OrderRequest request, OrderResponse response) {
        //clear cart
        Cart cart = cartRepository.findById(request.getCartId()).get();
        cart.getCartItems().clear();
        cartRepository.save(cart);

        //
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).get();
        Payment payment = paymentRepository.findById(response.getPaymentId()).get();

        // save the order
        Order order = Order.builder()
                .orderTime(LocalDateTime.now())
                .totalPrice(response.getTotalPrice())
                .orderStatus(OrderStatus.PURCHASED)
                .restaurant(restaurant)
                .payment(payment)
//                .delivery()
//                .customer()
                .build();

        Order savedOrder = orderRepository.save(order);
        payment.setOrder(savedOrder);

        response.setOrderId(savedOrder.getOrderId());
        response.setOrderTime(savedOrder.getOrderTime());
        response.setOrderStatus(savedOrder.getOrderStatus());
        log.info("Order has been placed successfully.");
        return handleNext(request,response);
    }

    @Override
    public OrderResponse handle(OrderRequest request, OrderResponse response) {
        return null;
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
