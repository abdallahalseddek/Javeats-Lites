package com.javaeat.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemAvailabilityResponse implements Serializable {
    private static final long serialVersionUID = -8122049585335550616L;
    private Integer itemId;
    private Boolean isAvailable;
}
