package com.javaeat.repository;

import com.javaeat.model.Menu;
import com.javaeat.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRespository extends JpaRepository<Payment,Integer> {
}
