package com.eCommerce.repository;

import com.eCommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public boolean existsByName(String name);
}
