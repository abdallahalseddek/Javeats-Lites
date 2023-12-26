package com.javaeat.repository;

import com.javaeat.model.Cart;
import com.javaeat.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findAllByCart_Id(Integer cartId);
}
