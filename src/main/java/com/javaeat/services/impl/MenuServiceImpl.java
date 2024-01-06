package com.javaeat.services.impl;

import com.javaeat.enums.ErrorMessage;
import com.javaeat.exception.NotFoundException;
import com.javaeat.model.Menu;
import com.javaeat.model.MenuItem;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final GenericMapper mapper;

    @Transactional
    @Override
    public MenuResponse addMenu(MenuRequest menuRequest) {
        if (menuRepository.findById(menuRequest.getId()).isPresent()) {
            throw new NotFoundException(HttpStatus.FORBIDDEN.value(),
                    ErrorMessage.MENU_ALREADY_EXISTS.name());
        }
        Menu menu = mapper.convert(menuRequest, Menu.class);
        Menu updatedMenu = setMenu(menuRequest, menu);
        menuRepository.save(updatedMenu);
        return mapper.convert(updatedMenu, MenuResponse.class);
    }

    @Transactional
    @Override
    public MenuItemResponse addMenuItem(MenuItemRequest menuItemRequest) {
        if (menuItemRepository.findById(menuItemRequest.getId()).isPresent()) {
            throw new NotFoundException(HttpStatus.FORBIDDEN.value(),
                    ErrorMessage.MENU_ITEM_ALREADY_EXISTS.name());
        }
        MenuItem menuItem = mapper.convert(menuItemRequest, MenuItem.class);
        MenuItem updatedMenuItem = setMenuItem(menuItem, menuItemRequest);
        MenuItem savedItem = menuItemRepository.save(updatedMenuItem);
        return mapper.convert(savedItem, MenuItemResponse.class);
    }

    @Transactional
    @Override
    public MenuItemResponse updateMenuItem(MenuItemRequest menuItemRequest) {
        MenuItem menuItem = checkMenuItemExists(menuItemRequest.getId());
        MenuItem updatedMenuItem = setMenuItem(menuItem, menuItemRequest);
        MenuItem savedItem = menuItemRepository.save(updatedMenuItem);
        return mapper.convert(savedItem, MenuItemResponse.class);
    }

    @Transactional
    @Override
    public void clearMenu(Integer menuId) {
        Menu menu = checkMenuExists(menuId);
        menuItemRepository.deleteMenuItemsByMenu(menu);
    }

    @Transactional
    @Override
    public void deleteMenu(Integer menuId) {
        clearMenu(menuId);
        Menu menu = checkMenuExists(menuId);
        menuRepository.delete(menu);
    }

    @Transactional
    @Override
    public void deleteMenuItem(Integer menuItemId) {
        MenuItem menuItem = checkMenuItemExists(menuItemId);
        menuItemRepository.delete(menuItem);
    }

    @Override
    public MenuItem checkMenuItemExists(Integer menuItemId) {
        return menuItemRepository
                .findById(menuItemId).orElseThrow(() ->
                        new NotFoundException(HttpStatus.NOT_FOUND.value()
                                , ErrorMessage.MENU_ITEM_NOT_FOUND.name()));
    }

    @Override
    public Menu checkMenuExists(Integer menuId) {
        return menuRepository
                .findById(menuId).orElseThrow(() ->
                        new NotFoundException(HttpStatus.NOT_FOUND.value()
                                , ErrorMessage.MENU_NOT_FOUND.name()));
    }

    private Menu setMenu(MenuRequest menuRequest, Menu menu) {
        menu.setId(menuRequest.getId());
        menu.setName(menuRequest.getName());
        menu.setDescription(menuRequest.getDescription());
        menu.setRestaurant(restaurantRepository.
                findById(menuRequest.getRestaurantId()).get());
        menu.setMenuItems(Collections.emptyList());
        menu.setCreatedBy(menuRequest.getCreatedBy());
        menu.setUpdatedBy(menuRequest.getUpdatedBy());
        menu.setCreationTime(LocalDateTime.now());
        menu.setLastUpdatedTime(LocalDateTime.now());
        return menu;
    }

    private MenuItem setMenuItem(MenuItem menuItem, MenuItemRequest menuItemRequest) {
        Menu menu = menuRepository.findById(menuItemRequest.getMenuId())
                .orElseThrow(() ->
                        new NotFoundException(HttpStatus.NOT_FOUND.value()
                                , ErrorMessage.MENU_NOT_FOUND.name()));
        menuItem.setMenu(menu);
        menuItem.setTitle(menuItemRequest.getTitle());
        menuItem.setIngredients(menuItemRequest.getIngredients());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setCartItem(null);
        menuItem.setCreatedBy(menu.getCreatedBy());
        menuItem.setUpdatedBy(menu.getUpdatedBy());
        return menuItem;
    }
}
