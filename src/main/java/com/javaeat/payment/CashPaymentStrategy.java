package com.javaeat.payment;

import com.javaeat.request.PaymentProcessRequest;

import java.util.Random;

public class CashPaymentStrategy  implements PaymentStrategy {
    @Override
    public boolean processPayment(PaymentProcessRequest paymentDetails) {
        // Randomly decide if payment succeeds or fails
        boolean success = new Random().nextBoolean();
        System.out.println("Processing cash payment... " + (success ? "Success" : "Failed"));
        return success;
    }
}
