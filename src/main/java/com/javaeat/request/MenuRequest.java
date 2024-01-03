package com.javaeat.request;

import com.javaeat.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {
    private Integer id;
    private String name;
    private String description;
    private Restaurant restaurant;

    // Base Entity Attributes
    private LocalDateTime creationTime;
    private LocalDateTime lastUpdatedTime;
    private String createdBy;
    private String updatedBy;
}
