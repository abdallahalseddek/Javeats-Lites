package com.javaeat.response;

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
public class ItemsAvailabilityResponse implements Serializable {
    private static final long serialVersionUID = -8122049585335550616L;

    private List<ItemAvailabilityResponse> itemsAvailabilityResponse;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static
    class ItemAvailabilityResponse{
        private Integer itemId;
        private Boolean isAvailable;
    }
}
