package com.javaeat.services.impl;

import com.javaeat.repository.MenuItemRepository;
import com.javaeat.repository.MenuRepository;
import com.javaeat.repository.RestaurantRepository;
import com.javaeat.util.GenericMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceImplTest {
    @Mock
    private  MenuRepository menuRepository;
    @Mock
    private  MenuItemRepository menuItemRepository;
    @Mock
    private  RestaurantRepository restaurantRepository;
    @Mock
    private  GenericMapper mapper;
    @InjectMocks
    private MenuServiceImpl menuService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void addMenu() {
    }

    @Test
    void addMenuItem() {
    }

    @Test
    void updateMenuItem() {
    }

    @Test
    void clearMenu() {
    }

    @Test
    void deleteMenu() {
    }

    @Test
    void deleteMenuItem() {
    }

    @Test
    void browseItemsInMenu() {
    }

    @Test
    void checkMenuItemExists() {
    }

    @Test
    void checkMenuExists() {
    }
}