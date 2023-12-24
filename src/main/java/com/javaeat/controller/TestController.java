package com.javaeat.controller;


import com.javaeat.request.TestRequest;
import com.javaeat.response.TestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("api/test")
@Slf4j
@Tag(name = "Test Endpoint")
public class TestController {

    @PostMapping("/sum")
    @Operation(summary = "Endpoint that sums two numbers",
            description = "Endpoint that accepts two numbers in the request body, and the summation as a response")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @ApiResponse(responseCode = "404", description = "Not found")
    @ApiResponse(responseCode = "500", description = "Server Error")
    public ResponseEntity<TestResponse> testEndpoint1(@Valid @RequestBody TestRequest request) {
        log.info("test endpoint 1");
        return ResponseEntity.ok(TestResponse.builder().num1(request.getNum1()).num2(request.getNum2()).sum(request.getNum1() + request.getNum2()).build());
    }
}
