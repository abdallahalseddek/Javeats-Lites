package com.javaeat.request;

import com.javaeat.response.ItemsAvailabilityResponse;
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
public class ItemsAvailabilityRequest implements Serializable {
    private static final long serialVersionUID = -8122049585335550616L;

    @NotNull
    private List<ItemAvailabilityRequest> itemsAvailabilityRequest;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class ItemAvailabilityRequest{
        private Integer itemId;
    }
}
