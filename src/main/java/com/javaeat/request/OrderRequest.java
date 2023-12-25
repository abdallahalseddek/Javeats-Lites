package com.javaeat.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class OrderRequest implements Serializable {
    private static final long serialVersionUID = -8122049585335550616L;

    private Integer customerId;
    private Integer restaurantId;
    private Integer deliveryId;
    private Integer paymentId;
    private List<CartItemRequest> items;
    private String deliveryAddress;


}
