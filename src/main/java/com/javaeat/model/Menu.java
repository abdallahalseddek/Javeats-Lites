package com.javaeat.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Menu")
@AllArgsConstructor
@Builder
public class Menu extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id",referencedColumnName = "restaurant_id")
    private Restaurant restaurant;
    @OneToMany(mappedBy = "menu")
    private List<MenuItem> menuItems=new ArrayList<>();
}
