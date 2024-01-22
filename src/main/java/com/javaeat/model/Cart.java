package com.javaeat.model;

import com.javaeat.enums.CartStatus;

import javax.persistence.*;

import com.javaeat.request.CartRequest;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart")
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class Cart extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer id;
    @Column(name = "total_price")
    private Double totalPrice ;
    @Column(name = "total_items")
    private Integer totalItems;
    @Column(name = "cart_status")
    @Enumerated(EnumType.STRING)
    private CartStatus status;
    @Column(name = "discount")
    private Double discount;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public static Cart buildCart(CartRequest cartRequest) {
        return Cart.builder()
                .status(cartRequest.getStatus())
                .totalItems(cartRequest.getTotalItems())
                .totalPrice(cartRequest.getTotalPrice())
                .discount(cartRequest.getDiscount())
                .build();
    }
}
