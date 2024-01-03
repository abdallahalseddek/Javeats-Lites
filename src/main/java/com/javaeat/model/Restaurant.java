package com.javaeat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Restaurant")
@AllArgsConstructor
public class Restaurant extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restaurant_id")
    private Integer id;
    @OneToOne(mappedBy = "restaurant")
    private RestaurantDetails restaurantDetails;
    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menus;
}