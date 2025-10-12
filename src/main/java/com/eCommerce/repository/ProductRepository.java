package com.eCommerce.repository;

import com.eCommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    public List<Product> findByIsActiveTrue();

    List<Product> findByCategoryAndIsActiveTrue(String category);

//    List<Product> findByTitleContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndIsActiveTrue(String title, String category);

    @Query("""
        SELECT p FROM Product p
        WHERE p.isActive = true
          AND (
                LOWER(p.title)    LIKE LOWER(CONCAT('%', :q, '%'))
             OR LOWER(p.category) LIKE LOWER(CONCAT('%', :q, '%'))
             OR LOWER(p.description) LIKE LOWER(CONCAT('%', :q, '%'))
          )
        """)
    List<Product> searchActiveProducts(@Param("q") String q);
}
