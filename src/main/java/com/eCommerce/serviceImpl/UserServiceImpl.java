package com.eCommerce.serviceImpl;

import com.eCommerce.entity.User;
import com.eCommerce.repository.UserRepository;
import com.eCommerce.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}
