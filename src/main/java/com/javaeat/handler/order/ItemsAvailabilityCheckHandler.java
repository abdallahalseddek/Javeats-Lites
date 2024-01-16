package com.javaeat.handler.order;

import com.javaeat.exception.HandlerException;
import com.javaeat.model.CartItem;
import com.javaeat.repository.CartItemRepository;
import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
@Builder
@Slf4j
@AllArgsConstructor
public class ItemsAvailabilityCheckHandler extends OrderHandler {

    private final CartItemRepository cartItemRepository;

    @Override
    public OrderResponse handle(OrderRequest request, OrderResponse response) {
        double totalPrice = request.getItems().stream()
                .mapToDouble(cartItemRequest -> {
                    CartItem item = cartItemRepository.findById(cartItemRequest.getId())
                            .orElseThrow(() -> new HandlerException("Item with ID " + cartItemRequest.getId() + " is not available."));

                    if (item.getQuantity() < cartItemRequest.getQuantity()) {
                        throw new HandlerException("Insufficient quantity for item ID " + cartItemRequest.getId());
                    }

                    return item.getUnitPrice() * cartItemRequest.getQuantity();
                })
                .sum();

        log.info("Total price of the order: " + totalPrice);
        response.setTotalPrice(totalPrice);

        return handleNext(request, response);
    }
}

