package com.javaeat.controller;


import com.javaeat.model.Menu;
import com.javaeat.request.MenuItemRequest;
import com.javaeat.request.MenuRequest;
import com.javaeat.response.MenuItemResponse;
import com.javaeat.response.MenuResponse;
import com.javaeat.services.MenuService;
import com.javaeat.util.MapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/v1/menu")
@Slf4j
@Tag(name = "Menu Endpoints")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final MapperUtil mapper;

    @PostMapping("/create")
    @Operation(summary = "Add a new Menu", description = "Add a new Menu")
    public ResponseEntity<MenuResponse> addMenu(@RequestBody @Valid MenuRequest menuRequest) {
        MenuResponse response = mapper.mapEntity(menuService.addMenu(menuRequest), MenuResponse.class);
        return new ResponseEntity<>(response, CREATED);
    }

    @PostMapping("/create/item")
    @Operation(summary = "Add a new Menu Item", description = "Add a new Menu Item")
    public ResponseEntity<MenuItemResponse> addMenuItem(@RequestBody @Valid MenuItemRequest menuItemRequest) {
        MenuItemResponse response = mapper.mapEntity(menuService.addMenuItem(menuItemRequest), MenuItemResponse.class);
        return new ResponseEntity<>(response, CREATED);

    }

    @PatchMapping("/update/item")
    @Operation(summary = "update Menu Item info", description = "update Menu Item info")
    public ResponseEntity<MenuItemResponse> updateMenuItem(@RequestBody @Valid MenuItemRequest menuItemRequest) {
        MenuItemResponse response = mapper.mapEntity(menuService.updateMenuItem(menuItemRequest), MenuItemResponse.class);
        return new ResponseEntity<>(response, ACCEPTED);

    }
    @GetMapping("/listItems/{menuId}")
    @Operation(summary = "list all Menu Items ", description = "list all Menu Items")
    public ResponseEntity<List<MenuItemResponse>> browseItemsInMenu(@PathVariable Integer menuId) {
        List<MenuItemResponse> responseList = mapper.mapList(menuService.browseItemsInMenu(menuId), MenuItemResponse.class);
        return new ResponseEntity<>(responseList, FOUND);
    }
    @GetMapping("/listMenus/{restaurantId}")
    @Operation(summary = "list menus of Restaurant", description = "list menus of Restaurant")
    public ResponseEntity<List<Menu>> listAllRestaurantMenus(@PathVariable Integer restaurantId) {
        return new ResponseEntity<>(menuService.browseAllRestaurantMenus(restaurantId), FOUND);
    }
    @DeleteMapping("/delete/{menuId}")
    @Operation(summary = "delete Restaurant menu", description = "delete Restaurant menu")
    public ResponseEntity<String> deleteMenu(@PathVariable Integer menuId) {
        menuService.deleteMenu(menuId);
        return new ResponseEntity<>("menu with id '" + menuId
                +"' is deleted successfully", OK);
    }
    @DeleteMapping("/clear/{menuId}")
    @Operation(summary = "Make menu Empty", description = "Make menu Empty")
    public ResponseEntity<String> clearMenu(@PathVariable Integer menuId) {
        menuService.clearMenu(menuId);
        return new ResponseEntity<>("menu with id '" + menuId
                +"' is cleared successfully", OK);
    }
    @DeleteMapping("/delete/item/{menuItemId}")
    @Operation(summary = "delete Menu Item", description = "delete Menu Item")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Integer menuItemId) {
        menuService.deleteMenuItem(menuItemId);
        return new ResponseEntity<>("Item with id '" + menuItemId
                +"' is deleted successfully", OK);
    }
}
