package com.javaeat.model;


import com.javaeat.enums.DeliveryStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name ="delivery")
@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Delivery extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private long deliveryId ;

    @Column(name = "estimated_time",nullable = false)
    private LocalDateTime estimatedTime;
    @Column(name = "delviery_status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status ;

    @ManyToOne
    @JoinColumn(name = "order_id" , referencedColumnName = "order_id")
    private Order order ;
}
