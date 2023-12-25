package com.javaeat.repository;

import com.javaeat.model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository  extends JpaRepository<Checkout, Integer> {
}
