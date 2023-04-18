package com.example.emag.model.repositories;

import com.example.emag.model.entities.OrderContent;
import com.example.emag.model.entities.OrderContentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderContentRepository extends JpaRepository<OrderContent, OrderContentKey> {

}
