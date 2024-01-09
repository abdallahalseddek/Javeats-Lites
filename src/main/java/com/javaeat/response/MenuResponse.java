package com.javaeat.response;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuResponse {
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
