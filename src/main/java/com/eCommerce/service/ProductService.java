package com.eCommerce.service;

import com.eCommerce.entity.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(int id);

    boolean deleteProduct(int id);

    List<Product> getAllActiveProducts(String category);

//    List<Product> searchProducts(String ch);

    List<Product> searchProducts(String ch);
}