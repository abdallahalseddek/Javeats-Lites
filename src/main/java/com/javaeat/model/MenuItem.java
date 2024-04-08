package com.javaeat.model;

import com.javaeat.request.MenuItemRequest;
import lombok.*;

import jakarta.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Menu_Item")
@AllArgsConstructor
@Builder
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
    @Column(name = "quantity")
    private Integer quantity;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id",referencedColumnName = "menu_id")
    private Menu menu;
    public static MenuItem itemBuilder(MenuItemRequest request){
        MenuItem menuItem = MenuItem.builder()
                .id(request.getId())
                .title(request.getTitle())
                .ingredients(request.getIngredients())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
        menuItem.setCreatedBy(request.getCreatedBy());
        menuItem.setUpdatedBy(request.getUpdatedBy());
        return menuItem;
    }
}
