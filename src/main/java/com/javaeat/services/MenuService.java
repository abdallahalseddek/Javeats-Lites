package com.javaeat.services;

import com.javaeat.request.MenuItemRequest;
import com.javaeat.request.MenuRequest;
import com.javaeat.response.MenuItemResponse;
import com.javaeat.response.MenuResponse;

public interface MenuService {
    MenuResponse addMenu(MenuRequest menuRequest);

    MenuItemResponse addMenuItem(MenuItemRequest menuItemRequest);

    MenuItemResponse updateMenuItem(MenuItemRequest menuItemRequest);

    void deleteMenu(Integer menuId);

    void clearMenu(Integer menuId);

    void deleteMenuItem(Integer menuItemId);


}
