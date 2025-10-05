package com.eCommerce.serviceImpl;

import com.eCommerce.entity.Cart;
import com.eCommerce.entity.Product;
import com.eCommerce.entity.User;
import com.eCommerce.repository.CartRepository;
import com.eCommerce.repository.ProductRepository;
import com.eCommerce.repository.UserRepository;
import com.eCommerce.service.CartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    @Override
    public Cart saveCart(Integer productId, Integer userId) {

        User user = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();

        Cart cart = cartRepository.findByProductIdAndUserId(productId,userId);

        if(cart == null){
            cart = new Cart();
            cart.setProduct(product);
            cart.setQuantity(1);
            cart.setUser(user);
            cart.setTotalPrice(product.getDiscountPrice());
        }
        else{
            cart.setQuantity(cart.getQuantity()+1);
            cart.setTotalPrice(cart.getQuantity()*cart.getProduct().getDiscountPrice());
        }
        return cartRepository.save(cart);
    }

    @Override
    public List<Cart> getCartByUser(Integer userId) {
        return List.of();
    }

    @Override
    public Integer getCartCount(Integer userId) {

        return cartRepository.countByUserId(userId);
    }
}
