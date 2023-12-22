package com.javaeat.model;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
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



    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "cart_id",referencedColumnName = "cart_id")
    private Cart cart;

    public CartItem(Integer quantity, Double unitPrice, Double totalPrice,Cart cart) {
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.cart=cart;
    }


//TODO: add menu item id
}
