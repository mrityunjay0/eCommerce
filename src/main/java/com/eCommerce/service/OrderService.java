package com.eCommerce.service;

import com.eCommerce.entity.OrderRequest;
import com.eCommerce.entity.ProductOrder;

import java.util.List;

public interface OrderService {

    List<ProductOrder> saveOrder(Integer userId, OrderRequest orderRequest);

    List<ProductOrder> getOrdersByUser(Integer uid);

    String cancelOrder(Integer id, Integer st);

    List<ProductOrder> getAllOrders();

    void updateOrderStatus(Integer orderId, String newStatus);
}