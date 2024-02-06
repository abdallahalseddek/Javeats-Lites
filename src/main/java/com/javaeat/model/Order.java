package com.javaeat.model;

import com.javaeat.enums.OrderStatus;
import com.javaeat.exception.HandlerException;
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
public class Order{
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
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id")
    private Customer customer ;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne
    private Payment payment;

    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "delivery_id")
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
                throw new HandlerException("Cannot update the status, the order has been cancelled");
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
