
package com.example.emag.model.repositories;


import com.example.emag.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>{
    List<Product> findAllByCategoryId(int categoryId);
    List<Product> getProductsByNameContaining(String name);

}
