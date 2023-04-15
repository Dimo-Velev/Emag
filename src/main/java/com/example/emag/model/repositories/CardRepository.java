package com.example.emag.model.repositories;

import com.example.emag.model.entities.Card;
import com.example.emag.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {

   Optional<Card> getCardByCardNumberAndUser(String cardNumber, User user);
}
