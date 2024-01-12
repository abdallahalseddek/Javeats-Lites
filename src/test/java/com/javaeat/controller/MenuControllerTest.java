package com.javaeat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaeat.model.Menu;
import com.javaeat.model.MenuItem;
import com.javaeat.model.Restaurant;
import com.javaeat.request.MenuItemRequest;
import com.javaeat.request.MenuRequest;
import com.javaeat.response.MenuItemResponse;
import com.javaeat.response.MenuResponse;
import com.javaeat.services.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MenuController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class MenuControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MenuService menuService;
    private Menu menu;
    private MenuItem menuItem;

    @BeforeEach
    void setUp() {
        Restaurant restaurant = Restaurant.builder().id(1)
                .restaurantDetails(RestaurantDetails.builder().build())
                .build();
        menu = Menu.builder().id(1)
                .name("menu 1").restaurant(restaurant).build();
        menuItem = MenuItem.builder().id(1)
                .menu(menu).build();
        MenuItem item1 = MenuItem.builder().menu(menu).build();
        MenuItem item2 = MenuItem.builder().menu(menu).build();
        List<MenuItem> itemList = List.of(item1, item2, menuItem);
        menu.setMenuItems(itemList);
    }

    @Test
    void should_addMenu_return_savedMenu_as_menuResponse() throws Exception {
        MenuRequest menuRequest = MenuRequest.builder().id(1).restaurantId(1)
                .createdBy("user test").name("menu test").build();
        MenuResponse menuResponse = MenuResponse.builder()
                .id(1).restaurantId(1)
                .createdBy("user test").name("menu test").build();
        when(menuService.addMenu(menuRequest)).thenReturn(menuResponse);
        mockMvc.perform(post("/api/menu/addMenu")
                        .content(objectMapper.writeValueAsString(menuRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_addMenuItem_return_MenuItemResponse() throws Exception {
        MenuItemRequest request = MenuItemRequest.builder().id(1).build();
        MenuItemResponse response = MenuItemResponse.builder().id(1).build();
        when(menuService.addMenuItem(request)).thenReturn(response);
        when(menuService.addMenuItem(request)).thenReturn(response);
        mockMvc.perform(post("/api/menu/addMenu")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void browseItemsInMenu_should_list_all_menuItems_in_a_Menu() throws Exception {
        MenuItemResponse response = new MenuItemResponse(1,
                "response item",
                "ingredients",
                20.0,
                1,
                null);
        when(menuService.browseItemsInMenu(1))
                .thenReturn(Collections.singletonList(response));
        mockMvc.perform(get("/api/menu/findAll/1"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void deleteMenu_should_remove_Menu_return_HttpOk() throws Exception {

        mockMvc.perform(delete("/api/menu/deleteMenu/" + menu.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void clearMenu_should_delete_allItems_without_deleting_menu() throws Exception {

        menuService.clearMenu(menu.getId());

        mockMvc.perform(delete("/api/menu/clearMenu/" + menu.getId()))
                .andDo(print())
                .andExpect(status().isOk());

        assertThat(menuService
                .browseItemsInMenu(menu.getId()))
                .isEmpty();
        assertThat(menu).isNotNull();
    }

    @Test
    void deleteMenuItem_should_delete_MenuItem_return_HttpOk() throws Exception {
        menuService.deleteMenu(menu.getId());

        mockMvc.perform(delete("/api/menu/deleteMenuItem/" + menuItem.getId()))
                .andDo(print())
                .andExpect(status().isOk());
    }
}