package com.javaeat.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Integer id;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "unit_price")
    private Double unitPrice;
    @Column(name = "total_price")
    private Double totalPrice;
    @ManyToOne
    @JoinColumn(name = "cart_id",referencedColumnName = "cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "checkout_id", referencedColumnName = "checkout_id")
    private Checkout checkout;
    public Double getTotalPrice() {
        return totalPrice != null ? totalPrice : 0.0;
    }



//TODO: add menu item id
}
