package com.javaeat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    private Cart cart;


//TODO: add menu item id
}
