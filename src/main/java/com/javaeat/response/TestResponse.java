package com.javaeat.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestResponse  implements Serializable {
    private static final long serialVersionUID = -8122049585335550616L;

    private Integer num1;

    private Integer num2;

    private Integer sum;

}