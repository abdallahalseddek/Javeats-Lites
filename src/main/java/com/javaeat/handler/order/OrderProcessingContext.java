package com.javaeat.handler.order;

import com.javaeat.model.*;
import com.javaeat.repository.*;
import com.javaeat.util.MapperUtil;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProcessingContext {
    private Cart cart;
    private List<CartItem> cartItems;
    private Restaurant restaurant;
    private Payment payment;
    private Order order;
    private MenuItem menuItem;


    private List<Runnable> deferredSaves = new ArrayList<>();

    public void addDeferredSave(Runnable saveAction) {
        deferredSaves.add(saveAction);
    }

    // Method to execute all deferred saves
    public void commitDeferredSaves() {
        deferredSaves.forEach(Runnable::run);
        deferredSaves.clear(); // Clear the actions once executed
    }

}
