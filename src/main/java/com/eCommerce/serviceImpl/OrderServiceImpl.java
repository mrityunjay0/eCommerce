package com.eCommerce.serviceImpl;

import com.eCommerce.entity.Cart;
import com.eCommerce.entity.OrderAddress;
import com.eCommerce.entity.OrderRequest;
import com.eCommerce.entity.ProductOrder;
import com.eCommerce.repository.CartRepository;
import com.eCommerce.repository.ProductOrderRepository;
import com.eCommerce.service.OrderService;
import com.eCommerce.utils.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private ProductOrderRepository productOrderRepository;
    private CartRepository cartRepository;

    public OrderServiceImpl(ProductOrderRepository productOrderRepository, CartRepository cartRepository) {
        this.productOrderRepository = productOrderRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<ProductOrder> saveOrder(Integer userId, OrderRequest orderRequest) {

        List<Cart> carts = cartRepository.findByUserId(userId);
        List<ProductOrder> created = new ArrayList<>();

        for(Cart c : carts) {
            ProductOrder productOrder = new ProductOrder();

            productOrder.setOrderId(UUID.randomUUID().toString());
            productOrder.setOrderDate(new Date());
            productOrder.setProduct(c.getProduct());
            productOrder.setUser(c.getUser());
            productOrder.setPrice(c.getProduct().getDiscountPrice());
            productOrder.setQuantity(c.getQuantity());
            productOrder.setStatus(OrderStatus.IN_PROGRESS.getDescription());
            productOrder.setPaymentMethod(orderRequest.getPaymentMethod());

            // Set order address

            OrderAddress orderAddress = new OrderAddress();

            orderAddress.setFirstName(orderRequest.getFirstName());
            orderAddress.setLastName(orderRequest.getLastName());
            orderAddress.setEmail(orderRequest.getEmail());
            orderAddress.setPhone(orderRequest.getPhone());
            orderAddress.setAddress(orderRequest.getAddress());
            orderAddress.setCity(orderRequest.getCity());
            orderAddress.setState(orderRequest.getState());
            orderAddress.setZipCode(orderRequest.getZipCode());

            productOrder.setOrderAddress(orderAddress);

            created.add(productOrderRepository.save(productOrder));
        }
        cartRepository.deleteByUserId(userId);
        return created;
    }

    @Override
    public List<ProductOrder> getOrdersByUser(Integer uid) {

        return productOrderRepository.findByUserId(uid);
    }

    @Override
    public String cancelOrder(Integer id, Integer st) {

        ProductOrder order = productOrderRepository.findById(id).orElse(null);
        if(order != null) {
            if(st == 8) {
                order.setStatus(OrderStatus.CANCELLED.getDescription());
            } else if(st == 9) {
                order.setStatus(OrderStatus.CANCELLED.getDescription());
            }
            productOrderRepository.save(order);
            return "Order cancelled successfully!";
        }
        return "Order not found!";
    }

    @Override
    public List<ProductOrder> getAllOrders() {

        return productOrderRepository.findAll();
    }

    @Override
    public void updateOrderStatus(Integer id, String newStatus) {

        ProductOrder order = productOrderRepository.findById(id).orElse(null);
        if(order != null) {
            order.setStatus(newStatus);
            productOrderRepository.save(order);
        }
    }

    @Override
    public ProductOrder getOrdersById(Integer id) {

        return productOrderRepository.findById(id).orElse(null);
    }
}
