package com.javaeat.response;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuItemResponse {
    private Integer id;
    private String title;
    private String ingredients;
    private Double price;
    private Integer menuId;
}
