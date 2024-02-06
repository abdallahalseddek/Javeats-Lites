package com.javaeat.payment;

import com.javaeat.request.PaymentProcessRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class CardPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(PaymentProcessRequest paymentDetails) {
        boolean success = new Random().nextBoolean();
        log.info("Processing card payment... " + (success ? "Success" : "Failed"));
        return success;
    }
}