package com.example.medhead.services;

import com.example.medhead.dao.UserRepository;
import com.example.medhead.models.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userName);

        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with username: " + userName);
        }
        return new CustomUserDetails(user);
    }
}
