package com.javaeat.response;

import com.javaeat.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse implements Serializable {
    private static final long serialVersionUID = -8122049585335550616L;

    private Integer cartId;
    private List<CartItemResponse> items;
    private CartStatus cartStatus;

}
