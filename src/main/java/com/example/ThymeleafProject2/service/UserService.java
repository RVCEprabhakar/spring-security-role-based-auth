package com.example.ThymeleafProject2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ThymeleafProject2.entity.User;
import com.example.ThymeleafProject2.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User saveUser(User user) {

        user.setPassword(
                passwordEncoder.encode(
                        user.getPassword()));

        return userRepository.save(user);
    }
}