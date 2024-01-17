package com.javaeat.handler.order;

import com.javaeat.enums.OrderStatus;
import com.javaeat.model.Order;
import com.javaeat.model.Payment;
import com.javaeat.model.Restaurant;
import com.javaeat.repository.CartRepository;
import com.javaeat.repository.OrderRepository;
import com.javaeat.repository.PaymentRepository;
import com.javaeat.repository.RestaurantRepository;
import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import com.javaeat.util.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Builder
@Slf4j
@AllArgsConstructor
public class FinalizeOrderHandler extends OrderHandler {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final PaymentRepository paymentRepository;
    private final RestaurantRepository restaurantRepository;
    private final MapperUtil mapperUtil;

    @Override
    public OrderResponse handle(OrderRequest request, OrderResponse response) {
        //clear cart
        cartRepository.deleteById(request.getCartId());

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
//        payment.setOrder(savedOrder);

        response.setOrderId(savedOrder.getOrderId());
        response.setOrderTime(savedOrder.getOrderTime());
        response.setOrderStatus(savedOrder.getOrderStatus());
        log.info("Order has been placed successfully.");
        return handleNext(request,response);
    }
}
