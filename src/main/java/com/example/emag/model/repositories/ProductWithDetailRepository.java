package com.example.emag.model.repositories;

import com.example.emag.model.entities.ProductWithDetail;
import com.example.emag.model.entities.ProductWithDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductWithDetailRepository extends JpaRepository<ProductWithDetail,ProductWithDetailKey> {

}
