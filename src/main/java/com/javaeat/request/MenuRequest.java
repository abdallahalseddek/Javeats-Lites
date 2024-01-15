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
    private String createdBy;
    private String updatedBy;
}
