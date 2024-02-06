package com.javaeat.payment;

import com.javaeat.request.PaymentProcessRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
@Slf4j
public class PayPalPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(PaymentProcessRequest paymentDetails) {
        boolean success = new Random().nextBoolean();
        log.info("Processing PayPal payment... " + (success ? "Success" : "Failed"));
        return success;
    }
}