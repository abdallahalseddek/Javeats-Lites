package com.javaeat.response;

import com.javaeat.enums.PaymentMethod;
import com.javaeat.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentProcessResponse {
    private Integer paymentId;
    private Integer orderId;
    private PaymentMethod method;
    private PaymentStatus status;
    private Double amount;
}
