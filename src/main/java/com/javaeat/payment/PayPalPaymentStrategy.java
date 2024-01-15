package com.javaeat.payment;

import com.javaeat.request.PaymentProcessRequest;

import java.util.Random;

public class PayPalPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(PaymentProcessRequest paymentDetails) {
        boolean success = new Random().nextBoolean();
        System.out.println("Processing PayPal payment... " + (success ? "Success" : "Failed"));
        return success;
    }
}