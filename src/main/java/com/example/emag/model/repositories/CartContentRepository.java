package com.example.emag.model.repositories;

import com.example.emag.model.entities.CartContent;
import com.example.emag.model.entities.CartContentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartContentRepository extends JpaRepository<CartContent, CartContentKey> {

    CartContent findByProductIdAndUserId(int id, int userId);
}
