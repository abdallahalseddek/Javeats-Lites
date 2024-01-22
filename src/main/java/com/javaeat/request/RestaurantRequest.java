package com.javaeat.request;

import com.javaeat.enums.Status;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
    private LocalTime OpeningTime;
    private LocalTime ClosingTime;
}
