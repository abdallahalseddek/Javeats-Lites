package com.javaeat.security.repository;

import com.javaeat.security.model.Token;
import com.javaeat.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByUser(User user);
    Optional<Token> findByToken(String token);
}
