package com.eCommerce.service;

import com.eCommerce.entity.Cart;

import java.util.List;

public interface CartService {

    public Cart saveCart(Integer productId, Integer userId);

    public List<Cart> getCartByUser(Integer userId);

    public Integer getCartCount(Integer userId);

    Boolean cartQuantityUpdate(String sy, Integer cid);
}
