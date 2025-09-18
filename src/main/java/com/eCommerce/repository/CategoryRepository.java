package com.eCommerce.repository;

import com.eCommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public boolean existsByName(String name);

    public List<Category> findByIsActiveTrue();
}
