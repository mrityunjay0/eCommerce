package com.eCommerce.repository;

import com.eCommerce.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {
}
