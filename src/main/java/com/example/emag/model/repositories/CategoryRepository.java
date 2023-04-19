package com.example.emag.model.repositories;

import com.example.emag.model.entities.Category;
import com.example.emag.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    boolean existsByNameIgnoreCase(String name);

}
