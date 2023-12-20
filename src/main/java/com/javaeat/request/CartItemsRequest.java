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
public class CartItemsRequest implements Serializable {
    private static final long serialVersionUID = -8122049585335550616L;

    @NotNull
    private Integer cartId;
    @NotNull
    private List<Integer> items;

}
