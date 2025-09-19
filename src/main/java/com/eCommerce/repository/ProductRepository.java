package com.eCommerce.repository;

import com.eCommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    public List<Product> findByIsActiveTrue();

    List<Product> findByCategoryAndIsActiveTrue(String category);
}
