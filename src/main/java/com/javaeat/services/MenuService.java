package com.javaeat.services;

import com.javaeat.model.Menu;
import com.javaeat.model.MenuItem;
import com.javaeat.request.MenuItemRequest;
import com.javaeat.request.MenuRequest;
import com.javaeat.response.MenuItemResponse;
import com.javaeat.response.MenuResponse;

import java.util.List;

public interface MenuService {
    Menu checkMenuExists(Integer menuId);

    MenuItem checkMenuItemExists(Integer menuItemId);

    MenuResponse addMenu(MenuRequest menuRequest);

    MenuItemResponse addMenuItem(MenuItemRequest menuItemRequest);

    MenuItemResponse updateMenuItem(MenuItemRequest menuItemRequest);

    void deleteMenu(Integer menuId);

    void clearMenu(Integer menuId);

    void deleteMenuItem(Integer menuItemId);
    List<MenuItemResponse> browseItemsInMenu(Integer menuId);
}
