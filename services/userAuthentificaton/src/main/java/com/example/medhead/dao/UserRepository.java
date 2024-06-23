package com.example.medhead.dao;

import com.example.medhead.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String userName);

    Optional<User> findByEmail(String email);
}
