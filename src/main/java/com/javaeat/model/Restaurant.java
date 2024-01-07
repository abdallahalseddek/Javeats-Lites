package com.javaeat.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Restaurant")
@AllArgsConstructor
@Builder
public class Restaurant extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer id;
    @OneToOne(mappedBy = "restaurant")
    private RestaurantDetails restaurantDetails;
    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menus = new ArrayList<>();
}
