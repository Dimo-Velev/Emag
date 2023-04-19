
package com.example.emag.model.repositories;


import com.example.emag.model.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer>{

    Optional<Review> findByIdAndUserId(int id, int userId);
}