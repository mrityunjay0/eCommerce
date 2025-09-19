package com.eCommerce.repository;

import com.eCommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByPhone(String phone);

    User findByEmail(String email);
}
