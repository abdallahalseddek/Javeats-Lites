package com.javaeat.handler.order;

import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public abstract class OrderHandler {
     OrderHandler next;


    public static OrderHandler processOrder(OrderHandler first, OrderHandler... chain) {
        OrderHandler head = first;
        for (OrderHandler nextInChain : chain) {
            log.info("assign to the next");
            head.setNext(nextInChain);
            head = nextInChain;
        }
        return first;
    }

    public abstract OrderResponse handle(OrderRequest request, OrderResponse response);

    /**
     * Runs check on the next object in chain or ends traversing if we're in
     * last object in chain.
     */
    protected OrderResponse handleNext(OrderRequest request, OrderResponse response) {
        if (next == null) {
            return response;
        }
        return next.handle(request,response);
    }
}
