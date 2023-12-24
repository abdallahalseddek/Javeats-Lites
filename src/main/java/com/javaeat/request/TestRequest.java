package com.javaeat.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestRequest {
    @NotNull
    private Integer num1;
    @NotNull
    private Integer num2;
}
