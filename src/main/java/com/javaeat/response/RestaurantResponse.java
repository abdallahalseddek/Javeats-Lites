package com.javaeat.response;

import com.javaeat.model.Menu;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantResponse{
    private Integer id;
    private String name;
    private String description;
    private String contactDetails;
    private String location;
    private List<Menu> menus;

    // Base Entity Attributes
    private LocalDateTime creationTime;
    private LocalDateTime lastUpdatedTime;
    private String createdBy;
    private String updatedBy;
}
