package com.javaeat.services;

import com.javaeat.model.Menu;
import com.javaeat.model.MenuItem;
import com.javaeat.request.MenuItemRequest;
import com.javaeat.request.MenuRequest;

import java.util.List;

public interface MenuService {
    Menu addMenu(MenuRequest menuRequest);

    MenuItem addMenuItem(MenuItemRequest menuItemRequest);

    MenuItem updateMenuItem(MenuItemRequest menuItemRequest);

    void deleteMenu(Integer menuId);

    void clearMenu(Integer menuId);

    void deleteMenuItem(Integer menuItemId);
    List<MenuItem> browseItemsInMenu(Integer menuId);
    List<Menu> browseAllRestaurantMenus(Integer restaurantId);

    List<MenuItem> findMenuItemByTitle(String title);
}
