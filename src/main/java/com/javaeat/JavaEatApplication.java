package com.javaeat;

import com.javaeat.enums.CartStatus;
import com.javaeat.model.Cart;
import com.javaeat.model.CartItem;
import com.javaeat.repository.CartItemRepository;
import com.javaeat.repository.CartRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@SpringBootApplication
public class JavaEatApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaEatApplication.class, args);
	}


}