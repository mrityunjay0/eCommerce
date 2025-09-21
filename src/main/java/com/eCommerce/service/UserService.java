package com.eCommerce.service;

import com.eCommerce.entity.User;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    boolean existsByPhone(String phone);

    public User getUserByEmail(String email);

//    public List<User> getAllUsers();

    public List<User> getAllUsersByRole(String role);

    public User getUserById(int id);
}