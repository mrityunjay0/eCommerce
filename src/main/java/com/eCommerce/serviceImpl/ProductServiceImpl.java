package com.eCommerce.serviceImpl;

import com.eCommerce.entity.Product;
import com.eCommerce.repository.ProductRepository;
import com.eCommerce.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public boolean deleteProduct(int id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product != null){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<Product> getAllActiveProducts(String category) {
        if(category != null && !category.isEmpty()){
            return productRepository.findByCategoryAndIsActiveTrue(category);
        }
        return productRepository.findByIsActiveTrue();
    }

    @Override
    public List<Product> searchProducts(String ch) {
        if (ch == null || ch.trim().isEmpty()) {
            return productRepository.findByIsActiveTrue();
        }
        return productRepository.searchActiveProducts(ch.trim());
    }
}
