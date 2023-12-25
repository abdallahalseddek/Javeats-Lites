package com.javaeat.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "checkout")
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "checkout_id")
    private Integer id;

    @OneToMany(mappedBy = "checkout", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    // Fields related to the shipping address
    @Column(name = "shipping_street")
    private String shippingStreet;

    @Column(name = "shipping_state")
    private String shippingState;

    @Column(name = "shipping_government")
    private String shippingGovernment;

    @Column(name = "shipping_contact_number")
    private String shippingContactNumber;



    // Other checkout-related fields (e.g., shipping details, order date, etc.)

}