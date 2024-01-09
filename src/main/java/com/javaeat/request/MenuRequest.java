package com.javaeat.request;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuRequest {
    private Integer id;
    private String name;
    private String description;
    private Integer restaurantId;

    // Base Entity Attributes
    private LocalDateTime creationTime;
    private LocalDateTime lastUpdatedTime;
    private String createdBy;
    private String updatedBy;
}
