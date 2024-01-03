package com.javaeat.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemRequest{
    private Integer id;
    private String title;
    private String ingredients;
    private Double price;
    private Integer menuId;
    private Integer cartItem;
}
