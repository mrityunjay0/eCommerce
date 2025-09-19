package com.eCommerce.repository;

import com.eCommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByPhone(String phone);
}
