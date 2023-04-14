
package com.example.emag.model.repositories;



        import com.example.emag.model.entities.ProductDetail;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailsRepository extends JpaRepository<ProductDetail,Integer>{
}