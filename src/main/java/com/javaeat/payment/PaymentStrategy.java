package com.javaeat.payment;

import com.javaeat.request.PaymentProcessRequest;

public interface PaymentStrategy {
    boolean processPayment(PaymentProcessRequest paymentDetails);
}
