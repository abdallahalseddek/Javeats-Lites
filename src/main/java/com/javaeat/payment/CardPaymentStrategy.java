package com.javaeat.payment;

import com.javaeat.request.PaymentProcessRequest;

import java.util.Random;

public class CardPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean processPayment(PaymentProcessRequest paymentDetails) {
        boolean success = new Random().nextBoolean();
        System.out.println("Processing card payment... " + (success ? "Success" : "Failed"));
        return success;
    }
}