package com.javaeat.request;

import com.javaeat.enums.Status;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantRequest {
    private Integer id;
    private String name;
    private String description;
    private Status status;
    private String location;
    private String createdBy;
    private String updatedBy;
}
