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
    private long orderId ;

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

    @OneToMany (mappedBy = "Orders")
    @JoinColumn(name = "restaurant_id")
    private List<Restaurant> restaurants;

    @OneToMany (mappedBy = "Orders")
    @JoinColumn(name = "payment_id")
    private List<Payment>payments ;

    @OneToMany (mappedBy = "Orders")
    @JoinColumn(name = "delivery_id")
    private List<Delivery>deliveries ;


}
