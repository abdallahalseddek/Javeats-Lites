package com.javaeat.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteOrderResponse implements Serializable {
    private static final long serialVersionUID = -8122049585335550616L;
    private Integer orderId;
    private Boolean isDeleted;
    private String note;
}
