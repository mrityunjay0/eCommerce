package com.eCommerce.service;

import com.eCommerce.entity.Product;

import java.util.List;

public interface ProductService {

    public Product saveProduct(Product product);

    List<Product> getAllProducts();

    public Product getProductById(int id);

    public boolean deleteProduct(int id);
}