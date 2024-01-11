package com.javaeat.model;

import com.javaeat.request.MenuRequest;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "Menu")
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
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
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "restaurant_id",referencedColumnName = "restaurant_id")
    private Restaurant restaurant;
    public static Menu menuBuilder(MenuRequest request){
        Menu menu = Menu.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .build();
        menu.setCreatedBy(request.getCreatedBy());
        menu.setUpdatedBy(request.getUpdatedBy());
        return menu;
    }
}
