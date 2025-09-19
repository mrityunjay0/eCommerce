package com.eCommerce.service;

import com.eCommerce.entity.User;

public interface UserService {

    public User saveUser(User user);

    boolean existsByPhone(String phone);

}