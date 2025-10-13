package com.eCommerce.serviceImpl;

import com.eCommerce.entity.User;
import com.eCommerce.repository.UserRepository;
import com.eCommerce.service.UserService;
import com.eCommerce.utils.AppConstant;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User saveUser(User user) {
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAccountNonLocked(true);
        user.setFailedAttempt(0);
        user.setLockTime(null);
        return userRepository.save(user);
    }

    @Override
    public User updateUserStatus(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUserPassword(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    @Override
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }

    @Override
    public List<User> getAllUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void increaseFailedAttempts(User user) {
        int failAttempts = user.getFailedAttempt() + 1;
        user.setFailedAttempt(failAttempts);
        userRepository.save(user);

    }

    @Override
    public void userAccountLock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        userRepository.save(user);
    }

    @Override
    public boolean unlockWhenTimeExpired(User user) {

        long lockTime = user.getLockTime().getTime();
        long unlockTime = lockTime + AppConstant.LOCK_TIME_DURATION;

        long currentTime = System.currentTimeMillis();

        if(unlockTime < currentTime){
            user.setAccountNonLocked(true);
            user.setFailedAttempt(0);
            user.setLockTime(null);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public void updateResetToken(String email, String resetToken) {
        User user = userRepository.findByEmail(email);
        user.setResetToken(resetToken);
        userRepository.save(user);
    }

    @Override
    public User getUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Override
    public User updateUser(User user) {

        return userRepository.save(user);
    }

    @Override
    public boolean changePassword(String oldPassword, String newPassword, int id) {

        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return false;
        }
        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public List<User> searchUsersByNameOrEmail(String ch) {

        return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(ch, ch);
    }

    @Override
    public void resetFailedAttempts(int id) {

    }
}
