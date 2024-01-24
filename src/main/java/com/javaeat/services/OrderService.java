package com.javaeat.services;


import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import com.javaeat.response.OrderStatusResponse;

public interface OrderService {


    OrderResponse getOrder(Integer orderId);

    OrderStatusResponse cancel(Integer orderId);

    OrderStatusResponse updateStatus(Integer orderId);

    OrderStatusResponse getStatus(Integer orderId);
}
