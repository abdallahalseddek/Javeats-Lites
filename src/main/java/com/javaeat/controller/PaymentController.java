package com.javaeat.controller;

import com.javaeat.request.PaymentProcessRequest;
import com.javaeat.response.PaymentProcessResponse;
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
@RequestMapping("api/payment")
@Slf4j
@Tag(name = "Payment Endpoints")
public class PaymentController {


    @PostMapping
    @Operation(summary = "Endpoint that makes a purchase",
            description = "Endpoint that makes a purchase to place an order")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    public ResponseEntity<PaymentProcessResponse> checkItemsAvailability(@Valid @RequestBody PaymentProcessRequest request) {
        //TODO: use strategy design pattern
        //return a fake status
        return ResponseEntity.ok(PaymentProcessResponse.builder().build());
    }

}
