package com.javaeat.services.impl;

import com.javaeat.enums.ErrorMessage;
import com.javaeat.exception.HandlerException;
import com.javaeat.exception.NotFoundException;
import com.javaeat.model.Menu;
import com.javaeat.model.MenuItem;
import com.javaeat.model.Restaurant;
import com.javaeat.repository.MenuItemRepository;
import com.javaeat.repository.MenuRepository;
import com.javaeat.repository.RestaurantRepository;
import com.javaeat.request.MenuItemRequest;
import com.javaeat.request.MenuRequest;
import com.javaeat.request.OrderRequest;
import com.javaeat.request.OrderResponse;
import com.javaeat.services.MenuService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MenuServiceImpl extends OrderHandler implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final LocalDateTime dateTime = LocalDateTime.now();

    @Transactional
    @Override
    public Menu addMenu(MenuRequest menuRequest) {
        isMenuExists(menuRequest.getId());
        Menu newMenu = Menu.menuBuilder(menuRequest);
        Restaurant restaurant = restaurantRepository.findById(menuRequest.getRestaurantId()).get();
        newMenu.setCreationTime(dateTime);
        newMenu.setRestaurant(restaurant);
        return menuRepository.save(newMenu);
    }

    @Transactional
    @Override
    public MenuItem addMenuItem(MenuItemRequest menuItemRequest) {
        isMenuItemExists(menuItemRequest.getId());
        MenuItem newItem = MenuItem.itemBuilder(menuItemRequest);
        Menu menu = menuRepository.findById(menuItemRequest.getMenuId()).get();
        newItem.setMenu(menu);
        return menuItemRepository.save(newItem);
    }

    @Transactional
    @Override
    public MenuItem updateMenuItem(MenuItemRequest menuItemRequest) {
        Menu menu = menuRepository.findById(menuItemRequest.getMenuId()).get();
        MenuItem updatedMenuItem = MenuItem.itemBuilder(menuItemRequest);
        updatedMenuItem.setMenu(menu);
        updatedMenuItem.setLastUpdatedTime(dateTime);
        return menuItemRepository.save(updatedMenuItem);
    }

    @Transactional
    @Override
    public void clearMenu(Integer menuId) {
        isMenuNotExists(menuId);
        Menu menu = menuRepository.findById(menuId).get();
        menuItemRepository.deleteMenuItemsByMenu(menu);
    }

    @Transactional
    @Override
    public void deleteMenu(Integer menuId) {
        clearMenu(menuId);
        Menu menu = menuRepository.findById(menuId).get();
        menuRepository.delete(menu);
    }

    @Transactional
    @Override
    public void deleteMenuItem(Integer menuItemId) {
        isMenuItemNotExists(menuItemId);
        MenuItem menuItem = menuItemRepository.findById(menuItemId).get();
        menuItemRepository.delete(menuItem);
    }

    @Override
    public List<MenuItem> browseItemsInMenu(Integer menuId) {
        isMenuNotExists(menuId);
        return menuItemRepository.findAllByMenuId(menuId);
    }

    @Override
    public List<Menu> browseAllRestaurantMenus(Integer restaurantId) {
        return menuRepository.findMenusByRestaurantId(restaurantId);
    }

    @Override
    public List<MenuItem> findMenuItemByTitle(String title) {
        isMenuItemNotExists(title);
        return menuItemRepository.findByTitleContaining(title);
    }

    public void isMenuItemExists(Integer menuItemId) {
        if (menuItemRepository.findById(menuItemId).isPresent()) {
            throw new NotFoundException(HttpStatus.FORBIDDEN.value(),
                    ErrorMessage.MENU_ITEM_ALREADY_EXISTS.name());
        }
    }

    public void isMenuItemNotExists(Integer menuItemId) {
        if (menuItemRepository.findById(menuItemId).isEmpty()) {
            throw new NotFoundException(HttpStatus.FORBIDDEN.value(),
                    ErrorMessage.MENU_ITEM_NOT_FOUND.name());}
    }
    public void isMenuItemNotExists(String title) {
        List<MenuItem> menuItems =menuItemRepository.findByTitleContaining(title);
        if(menuItems.isEmpty()) throw new NotFoundException(HttpStatus.FORBIDDEN.value(), ErrorMessage.MENU_ITEM_NOT_FOUND.name());
    }
    public void isMenuExists(Integer menuId) {
        if (menuRepository.findById(menuId).isPresent()) {
            throw new NotFoundException(HttpStatus.FORBIDDEN.value(),
                    ErrorMessage.MENU_ALREADY_EXISTS.name());
        }
    }

    public void isMenuNotExists(Integer menuId) {
        if (menuRepository.findById(menuId).isEmpty()) {
            throw new NotFoundException(HttpStatus.FORBIDDEN.value(),
                    ErrorMessage.MENU_NOT_FOUND.name());}
    }

    @Override
    @Transactional
    public OrderResponse handleOrder(OrderRequest request, OrderResponse response) {
        log.info("items validation handler");
        double totalPrice = request.getItems().stream()
                .mapToDouble(cartItemRequest -> {
                    MenuItem item = menuItemRepository.findById(cartItemRequest.getMenuItemId())
                            .orElseThrow(() -> new HandlerException("Item with ID " + cartItemRequest.getId() + " is not available."));

                    log.info("item.getQuantity() : {}",item.getQuantity() );
                    log.info("cartItemRequest.getQuantity() : {}",cartItemRequest.getQuantity() );
                    if (item.getQuantity() < cartItemRequest.getQuantity()) {
                        throw new HandlerException("Insufficient quantity for item ID " + cartItemRequest.getId());
                    }

                    return item.getPrice() * cartItemRequest.getQuantity();
                })
                .sum();

        // subtract the order quantity from the menu items
        request.getItems()
                .forEach(cartItemRequest -> {
                    MenuItem item = menuItemRepository.findById(cartItemRequest.getMenuItemId())
                            .orElseThrow(() -> new HandlerException("Item with ID " + cartItemRequest.getId() + " is not available."));
                    item.setQuantity(item.getQuantity() - cartItemRequest.getQuantity());
                    menuItemRepository.save(item);
                });

        log.info("Total price of the order: " + totalPrice);
        response.setTotalPrice(totalPrice);

        return handleNext(request, response);
    }

    @Override
    public OrderResponse handle(OrderRequest request, OrderResponse response) {
        return null;
    }
}
