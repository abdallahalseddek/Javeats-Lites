package com.javaeat.model;

import com.javaeat.enums.OrderStatus;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "Orders")
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId ;

    @Column(name = "order_time",nullable = false)
    private LocalDateTime orderTime;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer ;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery ;

    // Method to update the order status
    public void updateStatus() {
        switch (this.orderStatus) {
            case PURCHASED:
                this.orderStatus = OrderStatus.PREPARING;
                break;
            case PREPARING:
                this.orderStatus = OrderStatus.IN_DELIVERY;
                break;
            case IN_DELIVERY:
                this.orderStatus = OrderStatus.DELIVERED;
                break;
            case DELIVERED:
            case CANCELLED:
                // No action needed as these are final states
                break;
            default:
                throw new IllegalStateException("Unknown order status");
        }
    }

    public void cancelOrder() {
        if (this.orderStatus == OrderStatus.PURCHASED) {
            this.orderStatus = OrderStatus.CANCELLED;
        } else {
            throw new IllegalStateException("Order can only be cancelled if it is in PURCHASED state");
        }
    }

}
