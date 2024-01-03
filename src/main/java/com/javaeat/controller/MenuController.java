package com.javaeat.controller;


import com.javaeat.request.MenuItemRequest;
import com.javaeat.request.MenuRequest;
import com.javaeat.services.MenuService;
import com.javaeat.util.ApiResponse1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("api/menu")
@Slf4j
@Tag(name = "Menu Endpoints")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/addMenu")
    @Operation(summary = "Add a new Menu", description = "Add a new Menu")
    public ResponseEntity<ApiResponse1> addMenu(@RequestBody MenuRequest menuRequest) {
        return ApiResponse1.createUnifiedResponse(
                menuService.addMenu(menuRequest),
                HttpStatus.CREATED, "Successful operation",
                Arrays.asList("Bad request. Invalid input data",
                        "Menu not found or Item not Found",
                        "Internal server error. Something went wrong"));
    }

    @PatchMapping("/addMenuItem")
    @Operation(summary = "Add a new Menu Item", description = "Add a new Menu Item")
    public ResponseEntity<ApiResponse1> addMenuItem(@RequestBody MenuItemRequest menuItemRequest) {
        return ApiResponse1.createUnifiedResponse(
                menuService.addMenuItem(menuItemRequest),
                HttpStatus.CREATED, "Successful operation",
                Arrays.asList("Bad request. Invalid input data",
                        "Menu Item Info not found or Item not Found",
                        "Internal server error. Something went wrong"));
    }

    @PatchMapping("/updateMenuItem")
    @Operation(summary = "update Menu Item info", description = "update Menu Item info")
    public ResponseEntity<ApiResponse1> updateMenuItem(@RequestBody MenuItemRequest menuItemRequest) {
        return ApiResponse1.createUnifiedResponse(
                menuService.updateMenuItem(menuItemRequest),
                HttpStatus.CREATED, "Successful operation", Arrays.asList("Bad request. Invalid input data",
                        "Menu Item Info not found or Item not Found",
                        "Internal server error. Something went wrong"));
    }

    @DeleteMapping("/deleteMenu/{menuId}")
    @Operation(summary = "delete Restaurant menu", description = "delete Restaurant menu")
    public void deleteMenu(@PathVariable Integer menuId) {
        menuService.deleteMenu(menuId);
    }
    @DeleteMapping("/clearMenu/{menuId}")
    @Operation(summary = "Make menu Empty", description = "Make menu Empty")
    public void clearMenu(@PathVariable Integer menuId) {
        menuService.clearMenu(menuId);
    }
    @DeleteMapping("/deleteMenuItem/{menuItemId}")
    @Operation(summary = "delete Menu Item", description = "delete Menu Item")
    public void deleteMenuItem(@PathVariable Integer menuItemId) {
        menuService.deleteMenuItem(menuItemId);
    }
}
