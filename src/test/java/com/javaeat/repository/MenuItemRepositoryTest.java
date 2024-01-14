package com.javaeat.repository;

import com.javaeat.model.Menu;
import com.javaeat.model.MenuItem;
import com.javaeat.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MenuItemRepositoryTest {

    @Autowired
    private MenuItemRepository menuItemRepository;
    @Autowired
    private MenuRepository menuRepository;
    private MenuItem menuItem;

    // find all
//    @BeforeEach
//    void setUp() {
//        menuItem = MenuItem
//                .builder()
//                .title("item 1")
//                .ingredients("in 1")
//                .menu(new Menu()).build();
//    }
//
//    @Test
//    public void save_menuItem_and_return_SavedItem() {
//
//        MenuItem savedItem = menuItemRepository.save(menuItem);
//        assertThat(savedItem).isNotNull();
//        assertThat(savedItem.getId()).isGreaterThan(0);
//        assertEquals(savedItem.getId(), menuItem.getId());
//    }
//
//    @Test
//    void should_update_MenuItem_Return_UpdatedItem() {
//        MenuItem savedItem = menuItemRepository.save(menuItem);
//        MenuItem item = menuItemRepository.findById(savedItem.getId()).get();
//        item.setPrice(55.0);
//        item.setTitle("Honey spreed test update");
//        MenuItem updatedItem = menuItemRepository.save(item);
//        assertThat(updatedItem.getPrice()).isNotNull();
//        assertThat(updatedItem.getTitle()).isNotNull();
//
//    }
//
//    @Test
//    void get_item_owns_given_id() {
//
//        menuItemRepository.save(menuItem);
//
//        MenuItem savedItem = menuItemRepository.findById(menuItem.getId()).get();
//
//        assertThat(savedItem).isNotNull();
//        assertThat(savedItem.getId()).isGreaterThan(0);
//        assertEquals(savedItem.getId(), menuItem.getId());
//    }
//
//    @Test
//    void should_delete_AllMenuItems_for_menu() {
//        MenuItem saved = menuItemRepository.save(menuItem);
//        Menu menu = saved.getMenu();
//        menu.setRestaurant(new Restaurant());
//        menuRepository.save(menu);
//
//        menuItemRepository.deleteMenuItemsByMenu(menu);
//        Optional<MenuItem> itemReturn = menuItemRepository.findById(menuItem.getId());
//        assertThat(itemReturn).isEmpty();
//    }
//
//    @Test
//    void should_delete_MenuItem() {
//        MenuItem saved = menuItemRepository.save(menuItem);
//        Menu menu = saved.getMenu();
//        menu.setRestaurant(new Restaurant());
//        menuRepository.save(menu);
//
//        menuItemRepository.delete(menuItem);
//        Optional<MenuItem> itemReturn = menuItemRepository.findById(menuItem.getId());
//        assertThat(itemReturn).isEmpty();
//    }
//
//    @Test
//    void should_getAll_MenuItems_for_specific_Menu() {
//        Menu menu = new Menu(1, "test menu", "test desc", new Restaurant(), null);
//        menuRepository.save(menu);
//        MenuItem item1 = MenuItem.builder().title("item 1").ingredients("in 1").menu(menu).build();
//        MenuItem item2 = MenuItem.builder().title("item 2").ingredients("in 2").menu(menu).build();
//        MenuItem item3 = MenuItem.builder().title("item 3").ingredients("in 3").menu(menu).build();
//        MenuItem item4 =  MenuItem.builder().title("item 4").ingredients("in 4").menu(menu).build();
//        MenuItem item5 = MenuItem.builder().title("item 5").ingredients("in 5").menu(menu).build();
//        menuItemRepository.save(item1);menuItemRepository.save(item2);
//        menuItemRepository.save(item3);menuItemRepository.save(item4);
//        menuItemRepository.save(item5);
//
//        List<MenuItem> responseList = menuItemRepository.findAllByMenuId(menu.getId());
//
//        assertThat(responseList).isNotNull();
//        assertThat(responseList.size()).isEqualTo(5);
//    }
}