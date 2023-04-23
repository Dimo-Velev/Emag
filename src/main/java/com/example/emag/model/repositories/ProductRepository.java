
package com.example.emag.model.repositories;


import com.example.emag.model.DTOs.product.ProductViewDTO;
import com.example.emag.model.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAllByCategoryId(int categoryId, Pageable pageable);

    Page<Product> getProductsByNameStartsWith(String name, Pageable pageable);

    @Query(value = """
            SELECT p.*
            FROM users_viewed_products uvp
            INNER JOIN products p ON p.id = uvp.product_id
            WHERE uvp.user_id = :id""", nativeQuery = true)
    Page<Product> findProductsByUserId(@Param("id") int id, Pageable pageable);


    @Query(value = """
            SELECT p.* 
            FROM products p WHERE p.price BETWEEN :minPrice AND :maxPrice
            ORDER BY 
            CASE WHEN :order = 'ASC' THEN p.price END ASC,
            CASE WHEN :order = 'DESC' THEN p.price END DESC""",
            nativeQuery = true)
    Page<Product> filterMinMaxPrice(@Param("minPrice") int minValue,
                                    @Param("maxPrice") int maxValue,
                                    @Param("order") String order,
                                    Pageable pageable);

}
