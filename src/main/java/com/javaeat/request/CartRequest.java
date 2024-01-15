package com.javaeat.request;

import com.javaeat.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest {
    private Integer id;
    private Double totalPrice;
    private Integer totalItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CartStatus status;
    private Double discount;
    private String createdBy;
    private String updatedBy;
}
