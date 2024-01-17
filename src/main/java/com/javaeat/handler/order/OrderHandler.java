package com.javaeat.handler.order;

import com.javaeat.repository.*;
import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import com.javaeat.util.MapperUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public abstract class OrderHandler {
    protected OrderHandler next;


    public static OrderHandler link(OrderHandler first, OrderHandler... chain) {
        OrderHandler head = first;
        for (OrderHandler nextInChain : chain) {
            head.next = nextInChain;
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
