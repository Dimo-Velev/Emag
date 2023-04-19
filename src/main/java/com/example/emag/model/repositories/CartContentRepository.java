package com.example.emag.model.repositories;

import com.example.emag.model.entities.CartContent;
import com.example.emag.model.entities.CartContentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CartContentRepository extends JpaRepository<CartContent, CartContentKey> {

    Optional<CartContent> findByProductIdAndUserId(int id, int userId);
    Set<CartContent> findByUserId(int id);
    Optional<CartContent> deleteAllByUserId(int id);
}
