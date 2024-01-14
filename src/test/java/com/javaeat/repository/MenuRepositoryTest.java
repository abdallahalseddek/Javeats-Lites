package com.javaeat.repository;

import com.javaeat.model.Menu;
import com.javaeat.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;
    private Menu menu;

    @BeforeEach
    void setUp() {
        menu = Menu.builder()
                .name("test menu")
                .restaurant(new Restaurant())
                .build();
    }

    @Test
    public void should_save_Menu_return_savedMenu(){
        Menu savedMenu = menuRepository.save(menu);
        assertThat(savedMenu).isNotNull();
        assertThat(savedMenu.getId()).isGreaterThan(0);
        assertEquals(savedMenu.getId(),menu.getId());
    }

    @Test
    void should_update_Menu_return_updatedMenu() {
        Menu savedMenu = menuRepository.save(menu);
        savedMenu.setUpdatedBy("ahmed tester");
        savedMenu.setName("new test update");
        savedMenu.setLastUpdatedTime(LocalDateTime.now().minusDays(3));

        Menu updatedMenu = menuRepository.save(savedMenu);
        assertThat(updatedMenu.getUpdatedBy()).isNotNull();
        assertThat(updatedMenu.getName()).isNotNull();
        assertThat(updatedMenu.getLastUpdatedTime()).isNotNull();
    }

    @Test
    void should_delete_menu() {
        Menu savedMenu = menuRepository.save(menu);
        menuRepository.delete(savedMenu);
        Optional<Menu> returnMenu = menuRepository.findById(savedMenu.getId());
        assertThat(returnMenu).isEmpty();
    }

    @Test
    void should_get_Menu_by_given_id() {
         menuRepository.save(menu);

        Menu responseMenu = menuRepository.findById(menu.getId()).get();

        assertThat(responseMenu).isNotNull();
        assertThat(responseMenu.getId()).isGreaterThan(0);
        assertEquals(responseMenu.getId(),menu.getId());
    }

//    @Test
//    void should_get_allMenus_by_restaurantId() {
//        Menu menu1 = Menu.builder().name("test menu 1").restaurant(new Restaurant()).build();
//        Menu menu2 = Menu.builder().name("test menu 2").restaurant(new Restaurant()).build();
//        Menu menu3 = Menu.builder().name("test menu 3").restaurant(new Restaurant()).build();
//        Menu menu4 = Menu.builder().name("test menu 4").restaurant(new Restaurant()).build();
//        menuRepository.save(menu1);
//        menuRepository.save(menu2);
//        menuRepository.save(menu3);
//        menuRepository.save(menu4);
//
//        List<Menu> menuList = menuRepository.findByRestaurantId(menu.getRestaurant().getId());
//
//        assertThat(menuList.size()).isEqualTo(4);
//        assertThat(menuList).isNotNull();
//    }
}