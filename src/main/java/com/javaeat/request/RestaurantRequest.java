package com.javaeat.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest {
    private Integer id;
    private String name;
    private String description;
    private String contactDetails;
    private String location;

    // Base Entity Attributes
    private LocalDateTime creationTime;
    private LocalDateTime lastUpdatedTime;
    private String createdBy;
    private String updatedBy;
}
