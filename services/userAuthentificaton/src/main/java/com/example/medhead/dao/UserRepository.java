package com.example.medhead.dao;

import com.example.medhead.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String userName);

}
