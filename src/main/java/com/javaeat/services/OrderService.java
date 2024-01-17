package com.javaeat.services;

import com.javaeat.handler.order.OrderProcessResult;
import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import com.javaeat.response.OrderStatusResponse;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrder(Integer orderId);

    OrderStatusResponse cancel(Integer orderId);

    OrderStatusResponse updateStatus(Integer orderId);

    OrderStatusResponse getStatus(Integer orderId);
}
