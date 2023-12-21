package com.javaeat.model;

import com.javaeat.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
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

    // TODO: add @OneToOne customer
}
