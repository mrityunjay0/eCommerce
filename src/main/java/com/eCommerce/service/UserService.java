package com.eCommerce.service;

import com.eCommerce.entity.User;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    public User updateUserStatus(User user);

    public User updateUserPassword(User user);

    boolean existsByPhone(String phone);

    public User getUserByEmail(String email);

//    public List<User> getAllUsers();

    public List<User> getAllUsersByRole(String role);

    public User getUserById(int id);

    public void increaseFailedAttempts(User user);

    public void resetFailedAttempts(int id);

    public void userAccountLock(User user);

    public boolean unlockWhenTimeExpired(User user);

    public void updateResetToken(String email, String resetToken);

    public User getUserByResetToken(String resetToken);
}