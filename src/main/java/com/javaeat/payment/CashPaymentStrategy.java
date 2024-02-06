package com.javaeat.payment;

import com.javaeat.request.PaymentProcessRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
@Slf4j
public class CashPaymentStrategy  implements PaymentStrategy {
    @Override
    public boolean processPayment(PaymentProcessRequest paymentDetails) {
        // Randomly decide if payment succeeds or fails
        boolean success = new Random().nextBoolean();
        log.info("Processing cash payment... " + (success ? "Success" : "Failed"));
        return success;
    }
}
