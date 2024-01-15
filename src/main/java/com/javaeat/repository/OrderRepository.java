package com.javaeat.repository;

import com.javaeat.model.Menu;
import com.javaeat.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface OrderRepository extends JpaRepository<Order,Integer> {
}
