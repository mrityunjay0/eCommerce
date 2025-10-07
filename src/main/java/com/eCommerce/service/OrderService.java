package com.eCommerce.service;

import com.eCommerce.entity.OrderRequest;
import com.eCommerce.entity.ProductOrder;

public interface OrderService {

    public void saveOrder(Integer userId, OrderRequest orderRequest);
}
