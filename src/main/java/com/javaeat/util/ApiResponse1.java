package com.javaeat.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ApiResponse1 {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";

    private String status;
    private Object result;
    private int code;
    private String message;
    private List<String> errors;

    private static ApiResponse1 getApiResponseInstance(HttpStatus status) {
        ApiResponse1 response = new ApiResponse1();
        response.setStatus(status.is2xxSuccessful() ? SUCCESS : FAILED);
        response.setCode(status.value());
        return response;
    }

    public static  ResponseEntity<ApiResponse1> createUnifiedResponse(Object body, HttpStatus status, String message, List<String> errors) {
        ApiResponse1 response = getApiResponseInstance(status);
        response.setResult(body);
        response.setMessage(message);
        response.setErrors(errors);
        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<ApiResponse1> createUnifiedResponse(Object body, HttpStatus status, String message) {
        return createUnifiedResponse(body, status, message, null);
    }
}