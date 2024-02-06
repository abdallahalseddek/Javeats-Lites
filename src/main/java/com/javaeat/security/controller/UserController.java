package com.javaeat.security.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "User EndPoints")
public class UserController {

    @GetMapping
    public ResponseEntity<String> testAdmin(){
        return ResponseEntity.ok("Hello Restaurants!");
    }
}
