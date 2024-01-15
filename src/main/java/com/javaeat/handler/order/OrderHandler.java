package com.javaeat.handler.order;

import com.javaeat.request.OrderRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public abstract class OrderHandler {

    private OrderHandler next;

    public static OrderHandler link(OrderHandler first, OrderHandler... chain) {
        OrderHandler head = first;
        for (OrderHandler nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract boolean handle(OrderRequest request);

    /**
     * Runs check on the next object in chain or ends traversing if we're in
     * last object in chain.
     */
    protected boolean handleNext(OrderRequest request) {
        if (next == null) {
            return true;
        }
        return next.handle(request);
    }
}
