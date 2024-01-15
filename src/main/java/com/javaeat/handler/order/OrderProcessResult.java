package com.javaeat.handler.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderProcessResult {
    private  boolean success;
    private  String message;

    public static OrderProcessResult success() {
        return new OrderProcessResult(true, "Order placed successfully");
    }

    public static OrderProcessResult failure(String message) {
        return new OrderProcessResult(false, message);
    }
}
