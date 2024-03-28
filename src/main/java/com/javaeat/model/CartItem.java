package com.javaeat.model;

import com.javaeat.request.CartItemRequest;
import lombok.*;

import jakarta.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
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

    public Double getTotalPrice() {
        return totalPrice != null ? totalPrice : 0.0;
    }
    public static CartItem buildCartItem(CartItemRequest request){
       return CartItem.builder()
                .id(request.getId())
                .quantity(request.getQuantity())
                .unitPrice(request.getUnitPrice())
                .build();

    }
}
