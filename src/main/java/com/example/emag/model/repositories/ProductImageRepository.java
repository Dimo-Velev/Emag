package com.example.emag.model.repositories;

import com.example.emag.model.entities.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
}
