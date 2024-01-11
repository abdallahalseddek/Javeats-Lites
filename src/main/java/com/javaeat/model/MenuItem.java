package com.javaeat.model;

import com.javaeat.request.MenuItemRequest;
import lombok.*;

import javax.persistence.*;

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
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "menu_id",referencedColumnName = "menu_id")
    private Menu menu;
    public static MenuItem itemBuilder(MenuItemRequest request){
        MenuItem menuItem = MenuItem.builder()
                .id(request.getId())
                .title(request.getTitle())
                .ingredients(request.getIngredients())
                .price(request.getPrice())
                .build();
        menuItem.setCreatedBy(request.getCreatedBy());
        menuItem.setUpdatedBy(request.getUpdatedBy());
        return menuItem;
    }
}
