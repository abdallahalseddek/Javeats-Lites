package com.javaeat.services.impl;

import com.javaeat.enums.CartStatus;
import com.javaeat.enums.ErrorMessage;
import com.javaeat.enums.PaymentMethod;
import com.javaeat.enums.PaymentStatus;
import com.javaeat.exception.HandlerException;
import com.javaeat.exception.NotFoundException;
import com.javaeat.handler.order.OrderHandler;
import com.javaeat.model.Cart;
import com.javaeat.model.Payment;
import com.javaeat.payment.CardPaymentStrategy;
import com.javaeat.payment.CashPaymentStrategy;
import com.javaeat.payment.PayPalPaymentStrategy;
import com.javaeat.payment.PaymentStrategy;
import com.javaeat.repository.CartRepository;
import com.javaeat.repository.PaymentRepository;
import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import com.javaeat.services.PaymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentServiceImpl extends OrderHandler implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final CartRepository cartRepository;


    @Override
    public OrderResponse handle(OrderRequest request, OrderResponse response) {
        PaymentStrategy paymentStrategy = getPaymentStrategy(request.getPaymentDetails().getMethod());

        if (!paymentStrategy.processPayment(request.getPaymentDetails())) {
            log.info("Payment failed. Unlocking the cart.");
            //unlock the cart
            unlockCart(request.getCartId());
            throw new HandlerException("Payment failed. Unlocking the cart.");
        }

        Payment payment = paymentRepository.save(Payment.builder()
                .amount(response.getTotalPrice())
                .method(request.getPaymentDetails().getMethod())
                .status(PaymentStatus.SUCCESS)
                .build());

        response.setPaymentId(payment.getId());
        return handleNext(request,response);
    }

    private void unlockCart(Integer cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(
                () -> new NotFoundException(HttpStatus.NOT_FOUND.value(),
                        ErrorMessage.CART_NOT_FOUND.name()));

        cart.setStatus(CartStatus.READ_WRITE);
        cart.setLastUpdatedTime(LocalDateTime.now());
    }

    private PaymentStrategy getPaymentStrategy(PaymentMethod paymentType) {
        switch (paymentType) {
            case CASH:
                return new CashPaymentStrategy();
            case CARD:
                return new CardPaymentStrategy();
            case PAYPAL:
                return new PayPalPaymentStrategy();
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }
    }
}
