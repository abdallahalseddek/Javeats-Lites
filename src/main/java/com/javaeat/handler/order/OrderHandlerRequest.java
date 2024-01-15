package com.javaeat.handler.order;

import com.javaeat.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderHandlerRequest {
    private CartStatus cartLocked;
    private boolean itemsAvailable;
    private boolean restaurantOpen;
    // other properties like payment details, cart details, etc.

    // Constructors, getters, and setters

    public boolean isCartLocked() { return cartLocked.equals(CartStatus.READ_ONLY); }
    public boolean areItemsAvailable() { return itemsAvailable; }
    public boolean isRestaurantOpen() { return restaurantOpen; }
    public boolean processPayment() {
        // Implement payment processing logic
        return true; // Assuming payment succeeds for simplicity
    }
    public void unlockCart() { /* logic to unlock the cart */ }
    public void clearCart() { /* logic to clear the cart */ }
    public void createOrder() { /* logic to create the order */ }
}
