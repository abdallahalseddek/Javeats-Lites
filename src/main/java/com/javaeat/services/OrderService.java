package com.javaeat.services;

import com.javaeat.handler.order.OrderProcessResult;
import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);
}
