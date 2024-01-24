package com.javaeat.services.impl;

import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public abstract class OrderHandler {
     OrderHandler next;


    public static OrderHandler processOrder(OrderHandler first, OrderHandler... chain) {
        log.info("start initializing the chain of placing an order process...");
        OrderHandler head = first;
        for (OrderHandler nextInChain : chain) {
            head.setNext(nextInChain);
            head = nextInChain;
        }
        return first;
    }

    public abstract OrderResponse handleOrder(OrderRequest request, OrderResponse response);

    /**
     * Runs check on the next object in chain or ends traversing if we're in
     * last object in chain.
     */
    protected OrderResponse handleNext(OrderRequest request, OrderResponse response) {
        if (next == null) {
            return response;
        }
        return next.handleOrder(request,response);
    }
}
