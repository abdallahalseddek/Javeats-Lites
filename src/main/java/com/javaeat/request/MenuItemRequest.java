package com.javaeat.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemRequest{
    private Integer id;
    private String title;
    private String ingredients;
    private Double price;
    private Integer quantity;
    private Integer menuId;
    private Integer cartItem;
    private String createdBy;
    private String updatedBy;
}
