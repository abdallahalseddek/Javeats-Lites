package com.javaeat.services.impl;

import com.javaeat.repository.MenuItemRepository;
import com.javaeat.repository.MenuRepository;
import com.javaeat.repository.RestaurantRepository;
import com.javaeat.request.MenuItemRequest;
import com.javaeat.request.MenuRequest;
import com.javaeat.response.MenuItemResponse;
import com.javaeat.response.MenuResponse;
import com.javaeat.services.MenuService;
import com.javaeat.util.GenericMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final GenericMapper mapper;
    @Override
    public MenuResponse addMenu(MenuRequest menuRequest) {
        return null;
    }

    @Override
    public MenuItemResponse addMenuItem(MenuItemRequest menuItemRequest) {
        return null;
    }

    @Override
    public MenuItemResponse updateMenuItem(MenuItemRequest menuItemRequest) {
        return null;
    }

    @Override
    public void deleteMenu(Integer menuId) {

    }

    @Override
    public void clearMenu(Integer menuId) {

    }

    @Override
    public void deleteMenuItem(Integer menuItemId) {

    }
}
