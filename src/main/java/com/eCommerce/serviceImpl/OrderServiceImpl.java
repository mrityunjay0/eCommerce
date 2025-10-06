package com.eCommerce.serviceImpl;

import com.eCommerce.entity.Cart;
import com.eCommerce.entity.ProductOrder;
import com.eCommerce.repository.CartRepository;
import com.eCommerce.repository.ProductOrderRepository;
import com.eCommerce.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private ProductOrderRepository productOrderRepository;
    private CartRepository cartRepository;

    public OrderServiceImpl(ProductOrderRepository productOrderRepository, CartRepository cartRepository) {
        this.productOrderRepository = productOrderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public ProductOrder saveOrder(Integer userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);
        
    }
}
