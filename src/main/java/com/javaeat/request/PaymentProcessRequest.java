package com.javaeat.request;

import com.javaeat.enums.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProcessRequest {
    private Integer orderId;
    private PaymentMethod method;
    private Double amount;
}
