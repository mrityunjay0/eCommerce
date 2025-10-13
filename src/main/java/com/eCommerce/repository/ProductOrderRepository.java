package com.eCommerce.repository;

import com.eCommerce.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {

    List<ProductOrder> findByUserId(Integer uid);

    ProductOrder findByOrderId(String trim);
}
