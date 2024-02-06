package com.javaeat.response;

import com.javaeat.enums.Status;
import com.javaeat.model.Menu;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse {
    private Integer id;
    private String name;
    private String description;
    private Status status;
    private String location;
//    private List<Menu> menus;
    private String createdBy;
    private String updatedBy;
    private LocalTime OpeningTime;
    private LocalTime ClosingTime;
}
