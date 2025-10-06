package com.eCommerce.service;

import com.eCommerce.entity.ProductOrder;

public interface OrderService {

    public ProductOrder saveOrder(Integer userId);
}
