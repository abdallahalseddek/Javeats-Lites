package com.javaeat.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponse {
    private Integer id;
    private String title;
    private String ingredients;
    private Double price;
    private Integer menuId;
    private Integer cartItem;
}
