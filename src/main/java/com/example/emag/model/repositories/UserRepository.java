package com.example.emag.model.repositories;

import com.example.emag.model.entities.Product;
import com.example.emag.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);
    Optional<User> getByEmail(String email);

}
