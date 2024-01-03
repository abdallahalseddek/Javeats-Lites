package com.javaeat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Menu_Item")
@AllArgsConstructor
public class MenuItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_item_id")
    private Integer id;
    @Column(name = "title")
    private String title;
    @Column(name = "ingredients")
    private String ingredients;
    @Column(name = "price")
    private Double price;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id",referencedColumnName = "menu_id")
    private Menu menu;
    @OneToOne
    @JoinColumn(name = "cart_item_id",referencedColumnName = "cart_item_id")
    private CartItem cartItem;
}