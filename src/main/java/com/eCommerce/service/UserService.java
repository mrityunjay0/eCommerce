package com.eCommerce.service;

import com.eCommerce.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    User updateUserStatus(User user);

    User updateUserPassword(User user);

    boolean existsByPhone(String phone);

    User getUserByEmail(String email);

//    public List<User> getAllUsers();

    List<User> getAllUsersByRole(String role);

    User getUserById(int id);

    void increaseFailedAttempts(User user);

    void resetFailedAttempts(int id);

    void userAccountLock(User user);

    boolean unlockWhenTimeExpired(User user);

    void updateResetToken(String email, String resetToken);

    User getUserByResetToken(String resetToken);

    User updateUser(User user);

    boolean changePassword(String oldPassword, String newPassword, int id);

    List<User> searchUsersByNameOrEmail(String ch);
}