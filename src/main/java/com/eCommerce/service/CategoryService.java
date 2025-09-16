package com.eCommerce.service;

import com.eCommerce.entity.Category;

import java.util.List;

public interface CategoryService {

    public Category saveCategory(Category category);

    public List<Category> getAllCategories();

    public boolean existsByName(String name);

    public boolean deleteCategory(int id);

    public Category getCategoryById(int id);
}