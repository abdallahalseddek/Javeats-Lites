package com.javaeat.controller;


import com.javaeat.request.MenuItemRequest;
import com.javaeat.request.MenuRequest;
import com.javaeat.response.MenuItemResponse;
import com.javaeat.response.MenuResponse;
import com.javaeat.services.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/menu")
@Slf4j
@Tag(name = "Menu Endpoints")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/addMenu")
    @Operation(summary = "Add a new Menu", description = "Add a new Menu")
    public ResponseEntity<MenuResponse> addMenu(@RequestBody MenuRequest menuRequest) {
        return new ResponseEntity<>(menuService.addMenu(menuRequest),HttpStatus.CREATED);
    }

    @PostMapping("/addMenuItem")
    @Operation(summary = "Add a new Menu Item", description = "Add a new Menu Item")
    public ResponseEntity<MenuItemResponse> addMenuItem(@RequestBody MenuItemRequest menuItemRequest) {
        return new ResponseEntity<>(menuService.addMenuItem(menuItemRequest),HttpStatus.CREATED);

    }

    @PatchMapping("/updateMenuItem")
    @Operation(summary = "update Menu Item info", description = "update Menu Item info")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@RequestBody MenuItemRequest menuItemRequest) {
        return new ResponseEntity<>(menuService.updateMenuItem(menuItemRequest),HttpStatus.ACCEPTED);

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
