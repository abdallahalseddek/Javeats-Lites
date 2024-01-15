package com.javaeat.handler.order;

import com.javaeat.model.CartItem;
import com.javaeat.repository.CartItemRepository;
import com.javaeat.request.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Builder
@Slf4j
@AllArgsConstructor
public class ItemsAvailabilityCheckHandler  extends OrderHandler {


    private final CartItemRepository cartItemRepository;

    @Override
    public boolean handle(OrderRequest request) {
        boolean areItemsAvailable = request.getItems().stream().allMatch(cartItemRequest -> {
            Optional<CartItem> itemOpt = cartItemRepository.findById(cartItemRequest.getId());
            if(!itemOpt.isPresent()){
                log.info("Item with ID " + cartItemRequest.getId() + " is not available.");
                return false;
            }

            CartItem item = itemOpt.get();
            if (item.getQuantity() < cartItemRequest.getQuantity()){
                log.info("Insufficient quantity for item ID " + cartItemRequest.getId());
                return false;
            }

            return true;
        });


        if (!areItemsAvailable) {
            return false;
        }
        return handleNext(request);
    }
}
