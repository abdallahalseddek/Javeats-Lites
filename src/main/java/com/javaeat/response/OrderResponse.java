package com.javaeat.response;

import com.javaeat.enums.OrderStatus;
import com.javaeat.request.CartItemRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse implements Serializable {
    private static final long serialVersionUID = -8122049585335550616L;

    private Integer orderId;
    private Integer customerId;
    private Integer restaurantId;
    private Integer deliveryId;
    private Integer paymentId;
    private List<CartItemRequest> items;
    private Double totalPrice;
    private Date createdAt;
    private String deliveryAddress;
    private OrderStatus orderStatus;
}
