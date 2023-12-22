package com.javaeat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javaeat.enums.CartStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart")
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "cart_id")
    private Integer id;
    @Column(name = "total_price")
    private Double totalPrice = 0.0;
    @Column(name = "total_items")
    private Integer totalItems = 0;  // The count of items in the cart
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "cart_status")
    private CartStatus status;
    @Column(name = "discount")
    private Double discount;




    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    List<CartItem> cartItems;

    public void addCartItem(CartItem cartItem) {
        if(!this.cartItems.contains(cartItem)) {
            this.cartItems.add(cartItem);
            this.totalPrice+= cartItem.getUnitPrice();

            cartItem.setCart(this); // Set the reference to the Cart on the CartItem side

        }

    }

    public void removeCartItem(CartItem cartItem){
        if(cartItems.contains(cartItem)){
            cartItems.remove(cartItem);
            cartItem.setCart(this); // Set the reference to the Cart on the CartItem side

        }

    }
    // TODO: add @OneToOne customer
}
